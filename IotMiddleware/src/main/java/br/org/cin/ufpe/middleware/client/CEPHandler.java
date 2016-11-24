package br.org.cin.ufpe.middleware.client;

import java.util.HashMap;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import br.org.cin.ufpe.middleware.common.Constants;
import br.org.cin.ufpe.middleware.listener.SubscriptionListener;
import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.middleware.parser.ParserJson;
import br.org.cin.ufpe.middleware.pojo.ConnectionUtil;
import br.org.cin.ufpe.middleware.pojo.Entity;

public class CEPHandler {

	private Thread subscribeThread;
	private ConnectionFactory factory;
	private String exchange;
	private String routingKey;
	private String binding;
	private RabbitTemplate template;
	private Gson gson;

	public CEPHandler(ServiceAddress serviceAddress) {
		this.factory = ConnectionUtil.getConnectionFactory(serviceAddress);

		HashMap<String, String> extras = serviceAddress.getExtras();
		this.exchange = extras.get(Constants.QUEUE_EXCHANGE);
		this.routingKey = extras.get(Constants.ROUTING_KEY);
		this.binding = extras.get(Constants.BINDING);
		this.template = new RabbitTemplate();

		this.template.setConnectionFactory(ConnectionUtil.getCachedConnectionFactory(serviceAddress));
		this.gson = new Gson();

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

	public void publishUpdate(Entity entity) {

		String result = gson.toJson(entity, Entity.class);
		this.template.setRoutingKey(this.routingKey);
		template.convertAndSend(result);
	}

}
