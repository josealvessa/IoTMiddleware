import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.parser.ParserJson;

public class RabbitMQConnection {

	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	private Thread subscribeThread;
	private ConnectionFactory factory;
	private ServiceAddress address;

	private static final String EXCHANGE = "amq.direct";
	private static final String ROUTING_KEY = "si.test.queue";
	private static final String uri = "amqp://192.168.0.134:5672";

	public RabbitMQConnection() {
		setupConnectionFactory();
		setupRPC();
	}

	public void stopThread() {
		if (subscribeThread != null) {
			subscribeThread.stop();
		}
	}

	private void setupConnectionFactory() {

		this.factory = new ConnectionFactory();
		this.factory.setUsername("vhostuser");
		this.factory.setPassword("123456");
		this.factory.setUri(uri);
	}

	public void setupRPC(){
		
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    connection = factory.newConnection();
	    channel = connection.createChannel();

	    replyQueueName = channel.queueDeclare().getQueue(); 
	    consumer = new QueueingConsumer(channel);
	    channel.basicConsume(replyQueueName, true, consumer);
	    
	}
	
	public void subscribe(final SubscriptionListener listener) {

		this.subscribeThread = new Thread(new Runnable() {

			public void run() {

				while (true) {
					try {

						Connection connection = factory.newConnection();
						Channel channel = connection.createChannel();

						channel.basicQos(1);

						AMQP.Queue.DeclareOk q = channel.queueDeclare();

						channel.queueBind(ROUTING_KEY, EXCHANGE, "si.test.binding");
						QueueingConsumer consumer = new QueueingConsumer(channel);
						channel.basicConsume(ROUTING_KEY, true, consumer);

						while (true) {
							QueueingConsumer.Delivery delivery = consumer.nextDelivery();
							String message = new String(delivery.getBody());
							listener.onUpdate(ParserJson.parseEntity(message));
						}

					} catch (InterruptedException e) {
						break;
					} catch (Exception e1) {

						try {
							Thread.sleep(5000); // sleep and then try again
						} catch (InterruptedException e) {
							break;
						}
					}
				}
			}
		});

		subscribeThread.start();
	}

}
