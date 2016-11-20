package br.org.cin.ufpe.IoTCommonsProject.server.rabbitMQ;

import java.io.IOException;
import java.util.List;

import br.org.cin.ufpe.IoTCommonsProject.common.NamingServiceContract;
import br.org.cin.ufpe.IoTCommonsProject.dao.EntityDAO;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.Request;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Response;

public class RabbitMQRPCServerController {

	private EntityDAO dao;

	public RabbitMQRPCServerController() {
		dao = EntityDAO.getDAO();
	}

	public byte[] handleRequest(Request request) throws ClassNotFoundException, IOException {

		byte[] responseByteSequence = null;
		String entityId;
		Entity entity;

		Marshaller<Entity> entityMarshaller;
		Marshaller<List<Entity>> entitesMarshaller;

		String operation = request.getOperation();
		System.out.println("Operation = " + operation);

		switch (operation) {

		case NamingServiceContract.REGISTER:
			entityMarshaller = new Marshaller<Entity>();
			entity = entityMarshaller.unmarshall(request.getPayload());
			String registeredId = dao.save(entity);

			// TODO - Customize Error Code.
			if (registeredId == null) {
				throw new IOException();
			}

			entity.setId(registeredId);
			responseByteSequence = successResponse(entityMarshaller.marshall(entity));
			break;
		case NamingServiceContract.UNREGISTER:
			entityId = new String(request.getPayload());
			boolean unregisteredWithSuccess = dao.removeByField(entityId);
			responseByteSequence = successResponse(new byte[] { (byte) (unregisteredWithSuccess ? 1 : 0) });
			break;
		case NamingServiceContract.FIND:
			entityMarshaller = new Marshaller<Entity>();
			entity = entityMarshaller.unmarshall(request.getPayload());
			Entity foundEntity = dao.find(entity);
			responseByteSequence = successResponse(entityMarshaller.marshall(foundEntity));
			break;
		case NamingServiceContract.DISCOVER:
			entityMarshaller = new Marshaller<Entity>();
			entitesMarshaller = new Marshaller<List<Entity>>();
			entity = entityMarshaller.unmarshall(request.getPayload());
			List<Entity> entities = dao.discover(entity);
			responseByteSequence = successResponse(entitesMarshaller.marshall(entities));
			break;
		case NamingServiceContract.UPDATE:
			entityMarshaller = new Marshaller<Entity>();
			entity = entityMarshaller.unmarshall(request.getPayload());
			boolean updatedWithSuccess = dao.update(entity.getId(), entity);
			responseByteSequence = successResponse(new byte[] { (byte) (updatedWithSuccess ? 1 : 0) });

			break;
		}

		return responseByteSequence;
	}

	private byte[] successResponse(byte[] payload) throws IOException {
		Marshaller<Response> responseMarsallher = new Marshaller<Response>();
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setPayload(payload);
		return responseMarsallher.marshall(response);
	}

}
