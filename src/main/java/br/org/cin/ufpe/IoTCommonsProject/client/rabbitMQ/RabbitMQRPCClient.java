package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Response;

public class RabbitMQRPCClient {

	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	private QueueingConsumer consumer;
	private ConnectionFactory factory;

	public RabbitMQRPCClient(ConnectionFactory factory) throws IOException, TimeoutException {
		this.factory = factory;
		setupRPC();
	}

	public void setupRPC() throws IOException, TimeoutException {
		this.connection = this.factory.newConnection();
		this.channel = this.connection.createChannel();
		this.replyQueueName = this.channel.queueDeclare().getQueue();
		this.consumer = new QueueingConsumer(this.channel);
		this.channel.basicConsume(this.replyQueueName, true, this.consumer);
	}

	public Response register(Entity message) throws Exception {

		Response response = null;
		String corrId = java.util.UUID.randomUUID().toString();

		BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

		Marshaller<Entity> marshaller = new Marshaller<Entity>();
		channel.basicPublish("", requestQueueName, props, marshaller.marshall(message));

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				Marshaller<Response> responseMarshaller = new Marshaller<Response>();
				response = responseMarshaller.unmarshall(delivery.getBody());
				break;
			}
		}

		return response;
	}

	public void close() throws Exception {
		connection.close();
	}

}
