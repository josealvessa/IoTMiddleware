package br.org.cin.RabbitMQRestService;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Controller;

import br.org.cin.ufpe.IoTMiddleware.client.ClientController;
import br.org.cin.ufpe.IoTMiddleware.common.Constants;
import br.org.cin.ufpe.IoTMiddleware.naming.model.ServiceAddress;

@Controller
public class Service {
	
	private ClientController controller;

	public Service() {
		
		ServiceAddress nameServiceAddress = new ServiceAddress();
		nameServiceAddress.setHost("amqp://192.168.0.134:5672");
		nameServiceAddress.setUser("vhostuser");
		nameServiceAddress.setPassword("123456");

		HashMap<String, String> extras = new HashMap<String, String>();
		extras.put(Constants.QUEUE_NAME, Constants.IOT_MIDDLEWARE);
		extras.put(Constants.QUEUE_EXCHANGE, Constants.EXCHANGE_DEFAULT);
		extras.put(Constants.ROUTING_KEY, Constants.ROUTING_KEY_SAMPLE);
		extras.put(Constants.BINDING, Constants.DEFAULT_BINDING);

		nameServiceAddress.setExtras(extras);

		try {
			controller =  new ClientController(nameServiceAddress);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public ClientController getController(){
		return controller;
	}
}