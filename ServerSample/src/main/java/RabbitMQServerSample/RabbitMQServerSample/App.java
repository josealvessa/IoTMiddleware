package RabbitMQServerSample.RabbitMQServerSample;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import br.org.cin.ufpe.middleware.common.Constants;
import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.middleware.server.Server;

public class App {

	public static void main(String[] args) {

		ServiceAddress nameServiceAddress = new ServiceAddress();
		nameServiceAddress.setHost("amqp://192.168.0.134:5672");
		nameServiceAddress.setUser("vhostuser");
		nameServiceAddress.setPassword("123456");

		HashMap<String, String> queues = new HashMap<String, String>();
		queues.put(Constants.QUEUE_NAME, Constants.IOT_MIDDLEWARE);
		nameServiceAddress.setExtras(queues);

		Server server = new Server(nameServiceAddress);

		try {
			server.waitForConnections();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}