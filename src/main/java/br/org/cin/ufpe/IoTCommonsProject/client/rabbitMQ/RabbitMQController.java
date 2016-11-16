package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import br.org.cin.ufpe.IoTCommonsProject.common.NamingServiceContract;
import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.Request;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Response;

public class RabbitMQController {

	private RabbitMQConnection connection;
	private RabbitMQRPCClient rpcController;

	public RabbitMQController(ServiceAddress address) throws IOException, TimeoutException {
		this.connection = new RabbitMQConnection(address);
		this.rpcController = new RabbitMQRPCClient(address);
	}

	public void subscribe(final SubscriptionListener listener) {
		connection.subscribe(listener);
	}

	public void discoverDevices() {
		// Request all registered devices
		// Split by QUEUE
	}

	public Response register(Entity entity) throws Exception {

		Request request = new Request();
		Marshaller<Entity> marshaller = new Marshaller<Entity>();

		request.setOperation(NamingServiceContract.REGISTER);
		request.setPayload(marshaller.marshall(entity));

		Response response = this.rpcController.register(request);
		this.rpcController.close();
		return response;
	}

}
