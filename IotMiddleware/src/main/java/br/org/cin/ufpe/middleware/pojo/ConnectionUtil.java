package br.org.cin.ufpe.middleware.pojo;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import com.rabbitmq.client.ConnectionFactory;

import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;

public class ConnectionUtil {

	public static ConnectionFactory getConnectionFactory(ServiceAddress address) {

		ConnectionFactory factory = new ConnectionFactory();
		factory = new ConnectionFactory();
		factory.setUsername(address.getUser());
		factory.setPassword(address.getPassword());

		try {
			factory.setUri(address.getHost());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return factory;

	}

	public static 	org.springframework.amqp.rabbit.connection.ConnectionFactory getCachedConnectionFactory(ServiceAddress address) {

		CachingConnectionFactory factory = new CachingConnectionFactory("192.168.0.134");
		factory.setUsername(address.getUser());
		factory.setPassword(address.getPassword());
		return factory;

	}

	
}
