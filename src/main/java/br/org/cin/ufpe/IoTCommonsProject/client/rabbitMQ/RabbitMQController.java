package br.org.cin.ufpe.IoTCommonsProject.client.rabbitMQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import br.org.cin.ufpe.IoTCommonsProject.client.ClientInterface;
import br.org.cin.ufpe.IoTCommonsProject.common.NamingServiceContract;
import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.Request;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Response;
import br.org.cin.ufpe.IoTCommonsProject.server.rabbitMQ.ResponseStatus;

public class RabbitMQController implements ClientInterface {

	private RabbitMQConnection connection;
	private RabbitMQRPCClient rpcController;

	public RabbitMQController(ServiceAddress address) throws IOException, TimeoutException {
		this.connection = new RabbitMQConnection(address);
		this.rpcController = new RabbitMQRPCClient(address);
	}

	public void subscribe(final SubscriptionListener listener) {
		connection.subscribe(listener);
	}

	@Override
	public void subscribe(Entity entity, SubscriptionListener listener) {
		connection.subscribe(listener);
	}

	@Override
	public void subscribeToCEP(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribe(Entity entity) {

	}

	@Override
	public Entity register(Entity entity) throws IOException, Throwable {
		Marshaller<Entity> marshaller = new Marshaller<Entity>();
		Response response = null;

		try {
			response = executeRPCRequest(NamingServiceContract.REGISTER, marshaller.marshall(entity));

			if (response != null && response.getStatus() == ResponseStatus.OK) {
				entity = marshaller.unmarshall(response.getPayload());
			} else {
				// Throw exception!
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	@Override
	public List<Entity> discover(Entity entity) {
		Marshaller<Entity> marshaller = new Marshaller<Entity>();
		Marshaller<List<Entity>> entityListMarshaller = new Marshaller<List<Entity>>();
		List<Entity> entities = new ArrayList<Entity>();

		Response response = null;
		try {
			response = executeRPCRequest(NamingServiceContract.DISCOVER, marshaller.marshall(entity));

			if (response != null && response.getStatus() == ResponseStatus.OK) {
				entities = entityListMarshaller.unmarshall(response.getPayload());
			} else {
				// Throw exception!
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entities;
	}

	private Response executeRPCRequest(String operationName, byte[] payload) throws Exception {

		Request request = new Request();
		request.setOperation(operationName);
		request.setPayload(payload);

		Response response = this.rpcController.register(request);
		return response;
	}

	public void shutdown() throws Exception {
		this.rpcController.close();
	}

}
