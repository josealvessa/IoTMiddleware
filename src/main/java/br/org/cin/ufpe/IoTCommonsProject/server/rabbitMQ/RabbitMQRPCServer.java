package br.org.cin.ufpe.IoTCommonsProject.server.rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import br.org.cin.ufpe.IoTCommonsProject.common.Constants;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.Request;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.pojo.ConnectionUtil;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;

public class RabbitMQRPCServer {

	private String queueName;
	private ConnectionFactory factory;
	private RabbitMQRPCServerController controller;

	public RabbitMQRPCServer(ServiceAddress address) {
		this.factory = ConnectionUtil.getConnectionFactory(address);
		this.queueName = address.getExtras().get(Constants.QUEUE_NAME);
		this.controller = new RabbitMQRPCServerController();
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
