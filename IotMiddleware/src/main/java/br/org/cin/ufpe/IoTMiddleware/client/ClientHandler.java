package br.org.cin.ufpe.IoTMiddleware.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;

import br.org.cin.ufpe.IoTMiddleware.common.Constants;
import br.org.cin.ufpe.IoTMiddleware.naming.model.Request;
import br.org.cin.ufpe.IoTMiddleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTMiddleware.pojo.ConnectionUtil;
import br.org.cin.ufpe.IoTMiddleware.pojo.Marshaller;
import br.org.cin.ufpe.IoTMiddleware.pojo.Response;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ClientHandler {

	private Connection connection;
	private Channel channel;
	private String requestQueueName = "";
	private String replyQueueName;
	private QueueingConsumer consumer;
	private ConnectionFactory factory;

	public ClientHandler(ServiceAddress address) throws IOException, TimeoutException {
		this.factory = ConnectionUtil.getConnectionFactory(address);
		this.requestQueueName = address.getExtras().get(Constants.QUEUE_NAME);
		setupRPC();
	}

	public void setupRPC() throws IOException, TimeoutException {
		this.connection = this.factory.newConnection();
		this.channel = this.connection.createChannel();
		this.replyQueueName = this.channel.queueDeclare().getQueue();
		this.consumer = new QueueingConsumer(this.channel);
		this.channel.basicConsume(this.replyQueueName, true, this.consumer);
	}

	public Response register(Request request) throws Exception {

		Response response = null;
		String corrId = java.util.UUID.randomUUID().toString();

		BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

		Marshaller<Request> marshaller = new Marshaller<Request>();
		channel.basicPublish("", requestQueueName, props, marshaller.marshall(request));

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
