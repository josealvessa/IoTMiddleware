package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Response;

public class RabbitMQController {

	private RabbitMQConnection connection;
	private String queuName;
	private String rpcQueue;
	private ConnectionFactory factory;
	private RabbitMQRPCClient rpcController;

	public RabbitMQController(String queueName, String rpcQueue) throws IOException, TimeoutException {

		this.factory = new ConnectionFactory();
		this.factory.setUsername("vhostuser");
		this.factory.setPassword("123456");

		try {
			this.factory.setUri("amqp://192.168.0.134:5672");
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

		this.queuName = queuName;
		this.rpcQueue = rpcQueue;
		this.connection = new RabbitMQConnection(this.factory);
		this.rpcController = new RabbitMQRPCClient(this.factory);

	}

	public void subscribe(final SubscriptionListener listener) {
		connection.subscribe(listener);
	}

	public void discoverDevices() {
		// Request all registered devices
		// Split by QUEUE
	}

	public Response register(Entity entity) throws Exception {
		Response response = this.rpcController.register(entity);
		this.rpcController.close();
		return response;
	}

}
