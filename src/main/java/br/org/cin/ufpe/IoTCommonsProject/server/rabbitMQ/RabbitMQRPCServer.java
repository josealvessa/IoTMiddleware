package br.org.cin.ufpe.IoTCommonsProject.server.rabbitMQ;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Response;

public class RabbitMQRPCServer {

	private String queueName = "rpc_queue";
	private ConnectionFactory factory;

	public RabbitMQRPCServer(String queueName) {

		factory = new ConnectionFactory();
		factory.setUsername("vhostuser");
		factory.setPassword("123456");

		try {
			factory.setUri("amqp://192.168.0.134:5672");
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.queueName = queueName;
		// this.factory = factory;
	}

	public void waitForAvailableConnections()
			throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {

		System.out.println(" [x] Awaiting RPC requests");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(queueName, false, false, false, null);

		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, false, consumer);

		Marshaller<Entity> entityMarshaller = new Marshaller<Entity>();
		Marshaller<Response> responseEntityMarshaller = new Marshaller<Response>();

		while (true)

		{
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();

			BasicProperties props = delivery.getProperties();
			BasicProperties replyProps = new BasicProperties.Builder().correlationId(props.getCorrelationId()).build();

			Entity entity = entityMarshaller.unmarshall(delivery.getBody());
			System.out.println("=> ReceivedEntity" + entity);

			channel.basicPublish("", props.getReplyTo(), replyProps,
					responseEntityMarshaller.marshall(Response.getSuccessResponse()));

			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}

	}
}
