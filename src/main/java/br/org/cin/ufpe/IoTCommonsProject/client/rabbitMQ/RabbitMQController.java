package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.ConnectionFactory;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;

public class RabbitMQController {

	private RabbitMQConnection connection;
	private String queuName;
	private ConnectionFactory factory;

	public RabbitMQController() {

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

		this.connection = new RabbitMQConnection(this.factory);
	}

	public RabbitMQController(String queuName) {
		this();
		this.queuName = queuName;
	}

	public void subscribe(final SubscriptionListener listener) {
		connection.subscribe(listener);
	}

	public void discoverDevices() {
		// Request all registered devices
		// Split by QUEUE
	}

	public void register() {
		// Assign the device using.
	}

}
