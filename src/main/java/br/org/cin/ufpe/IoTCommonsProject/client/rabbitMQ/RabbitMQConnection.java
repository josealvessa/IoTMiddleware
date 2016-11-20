package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import java.util.HashMap;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import br.org.cin.ufpe.IoTCommonsProject.common.Constants;
import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.parser.ParserJson;
import br.org.cin.ufpe.IoTCommonsProject.pojo.ConnectionUtil;

public class RabbitMQConnection {

	private Thread subscribeThread;
	private ConnectionFactory factory;

	private String exchange;
	private String routingKey;
	private String binding;

	public RabbitMQConnection(ServiceAddress serviceAddress) {
		this.factory = ConnectionUtil.getConnectionFactory(serviceAddress);

		HashMap<String, String> extras = serviceAddress.getExtras();
		this.exchange = extras.get(Constants.QUEUE_EXCHANGE);
		this.routingKey = extras.get(Constants.ROUTING_KEY);
		this.binding = extras.get(Constants.BINDING);
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

						channel.queueBind(routingKey, exchange, binding);
						QueueingConsumer consumer = new QueueingConsumer(channel);
						channel.basicConsume(routingKey, true, consumer);

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
