package br.org.cin.ufpe.middleware.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import br.org.cin.ufpe.middleware.common.NamingServiceContract;
import br.org.cin.ufpe.middleware.common.ResponseStatus;
import br.org.cin.ufpe.middleware.listener.SubscriptionListener;
import br.org.cin.ufpe.middleware.naming.model.Request;
import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.middleware.pojo.Entity;
import br.org.cin.ufpe.middleware.pojo.Marshaller;
import br.org.cin.ufpe.middleware.pojo.Response;

public class ClientController implements ClientInterface {

	private CEPHandler connection;
	private ClientHandler rpcController;

	public ClientController(ServiceAddress address) throws IOException, TimeoutException {
		this.connection = new CEPHandler(address);
		this.rpcController = new ClientHandler(address);
	}

	public void subscribe(final SubscriptionListener listener) {
		connection.subscribe(listener);
	}

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

	@Override
	public void publish(Entity entity) {
		connection.publishUpdate(entity);
	}

}
