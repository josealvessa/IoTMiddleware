package br.org.cin.ufpe.IoTCommonsProject.server.rabbitMQ;

import java.io.IOException;

import br.org.cin.ufpe.IoTCommonsProject.common.NamingServiceContract;
import br.org.cin.ufpe.IoTCommonsProject.dao.EntityDAO;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.Request;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Marshaller;

public class RabbitMQRPCServerController {

	private EntityDAO dao;

	public RabbitMQRPCServerController() {
		dao = EntityDAO.getDAO();
	}

	public byte[] handleRequest(Request request) throws ClassNotFoundException, IOException {

		byte[] response = null;
		String entityId;
		Entity entity;
		Marshaller<Entity> entityMarshaller;

		String operation = request.getOperation();

		switch (operation) {

		case NamingServiceContract.REGISTER:
			entityMarshaller = new Marshaller<>();
			entity = entityMarshaller.unmarshall(request.getPayload());
			boolean registedWithSuccess = dao.save(entity);
			response = new byte[] { (byte) (registedWithSuccess ? 1 : 0) };

			break;
		case NamingServiceContract.UNREGISTER:
			entityId = new String(request.getPayload());
			boolean unregisteredWithSuccess = dao.removeByField(entityId);
			response = new byte[] { (byte) (unregisteredWithSuccess ? 1 : 0) };
			break;
		case NamingServiceContract.FIND:
			entityId = new String(request.getPayload());
			Entity serviceAddress = dao.find(entityId);
			entityMarshaller = new Marshaller<>();
			response = entityMarshaller.marshall(serviceAddress);
			break;
		case NamingServiceContract.UPDATE:
			entityMarshaller = new Marshaller<>();
			entity = entityMarshaller.unmarshall(request.getPayload());
			boolean updatedWithSuccess = dao.update(entity.getId(), entity);
			response = new byte[] { (byte) (updatedWithSuccess ? 1 : 0) };
			break;
		}

		return response;
	}

}
