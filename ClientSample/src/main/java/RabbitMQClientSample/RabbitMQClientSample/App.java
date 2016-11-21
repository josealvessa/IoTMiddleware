package RabbitMQClientSample.RabbitMQClientSample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import br.org.cin.ufpe.IoTMiddleware.client.ClientController;
import br.org.cin.ufpe.IoTMiddleware.common.Constants;
import br.org.cin.ufpe.IoTMiddleware.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTMiddleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTMiddleware.pojo.Attributes;
import br.org.cin.ufpe.IoTMiddleware.pojo.Entity;

public class App {
	public static void main(String[] args) throws Throwable {
		try {

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

			List<Attributes> attributes = new ArrayList<Attributes>();
			Attributes attribute = new Attributes("Temperature", "String", "43", null);
			attributes.add(attribute);

			Entity entity = new Entity();
			entity.setAttributes(attributes);
			entity.setType("TEMPERATURE");

			ClientController controller = new ClientController(nameServiceAddress);
			Entity registeredEntity = controller.register(entity);
			System.out.println(registeredEntity);

			Entity searchableEntity = new Entity();
			entity.setType("TEMPERATURE");
			List<Entity> entitiesAvailable = controller.discover(searchableEntity);
			System.out.println(entitiesAvailable);

			controller.shutdown();
			
			controller.subscribe(new SubscriptionListener() {
				
				public void onUpdate(Entity entity) {
					System.out.println("OnUpdate =>" + entity);
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
