package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.parser.ParserJson;
import br.org.cin.ufpe.IoTCommonsProject.pojo.ConnectionUtil;

public class RabbitMQConnection {

	private Thread subscribeThread;
	private ConnectionFactory factory;

	private static final String EXCHANGE = "amq.direct";
	private static final String ROUTING_KEY = "si.test.queue";

	public RabbitMQConnection(ServiceAddress serviceAddress) {
		this.factory = ConnectionUtil.getConnectionFactory(serviceAddress);
	}

	public void stopThread() {
		if (subscribeThread != null) {
			subscribeThread.stop();
		}
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
