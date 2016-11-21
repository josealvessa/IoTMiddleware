package br.org.cin.ufpe.IoTMiddleware.server;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;

import br.org.cin.ufpe.IoTMiddleware.common.Constants;
import br.org.cin.ufpe.IoTMiddleware.naming.model.Request;
import br.org.cin.ufpe.IoTMiddleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTMiddleware.pojo.ConnectionUtil;
import br.org.cin.ufpe.IoTMiddleware.pojo.Marshaller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Server {

	private String queueName;
	private ConnectionFactory factory;
	private ServerController controller;

	public Server(ServiceAddress address) {
		this.factory = ConnectionUtil.getConnectionFactory(address);
		this.queueName = address.getExtras().get(Constants.QUEUE_NAME);
		this.controller = new ServerController();
	}

	public void waitForConnections() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {

		System.out.println("[Naming Service] Initializing Service");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(queueName, false, false, false, null);
		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, false, consumer);

		Marshaller<Request> requestMarshaller = new Marshaller<Request>();

		while (true)
		{
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			BasicProperties props = delivery.getProperties();
			BasicProperties replyProps = new BasicProperties.Builder().correlationId(props.getCorrelationId()).build();

			Request request = requestMarshaller.unmarshall(delivery.getBody());
			byte[] response = this.controller.handleRequest(request);

			System.out.println("=> Request" + request);
			channel.basicPublish("", props.getReplyTo(), replyProps, response);
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
		
	}
}
