package br.org.cin.ufpe.IoTMiddleware.pojo;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.ConnectionFactory;

import br.org.cin.ufpe.IoTMiddleware.naming.model.ServiceAddress;

public class ConnectionUtil  {

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
}
