package br.org.cin.ufpe.middleware.naming;

import java.io.IOException;

import br.org.cin.ufpe.middleware.common.NamingServiceContract;
import br.org.cin.ufpe.middleware.dao.NameServiceDAO;
import br.org.cin.ufpe.middleware.naming.model.Request;
import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;
import br.org.cin.ufpe.middleware.pojo.Marshaller;

public class NameServiceController implements NamingServiceInterface {

	public boolean register(ServiceAddress service) {
		return NameServiceDAO.getDAO().save(service) != null;
	}

	public boolean unregister(String serviceName) {
		return NameServiceDAO.getDAO().removeByField(serviceName);
	}

	public ServiceAddress find(String serviceName) {
		return NameServiceDAO.getDAO().getServiceByName(serviceName);
	}

	public boolean update(ServiceAddress service) {
		return NameServiceDAO.getDAO().update(service.getName(), service);
	}

	public byte[] handleRequest(Request request) throws ClassNotFoundException, IOException {

		byte[] response = null;
		String serviceName;
		ServiceAddress addressToRegister;
		Marshaller<ServiceAddress> serviceAddressMarshaller;

		String operation = request.getOperation();

		switch (operation) {

		case NamingServiceContract.REGISTER:
			serviceAddressMarshaller = new Marshaller<ServiceAddress>();
			addressToRegister = serviceAddressMarshaller.unmarshall(request.getPayload());
			boolean registedWithSuccess = register(addressToRegister);
			response = new byte[] { (byte) (registedWithSuccess ? 1 : 0) };

			break;
		case NamingServiceContract.UNREGISTER:
			serviceName = new String(request.getPayload());
			boolean unregisteredWithSuccess = unregister(serviceName);
			response = new byte[] { (byte) (unregisteredWithSuccess ? 1 : 0) };
			break;
		case NamingServiceContract.FIND:
			serviceName = new String(request.getPayload());
			ServiceAddress serviceAddress = find(serviceName);
			serviceAddressMarshaller = new Marshaller<ServiceAddress>();
			response = serviceAddressMarshaller.marshall(serviceAddress);
			break;
		case NamingServiceContract.UPDATE:
			serviceAddressMarshaller = new Marshaller<ServiceAddress>();
			addressToRegister = serviceAddressMarshaller.unmarshall(request.getPayload());
			boolean updatedWithSuccess = update(addressToRegister);
			response = new byte[] { (byte) (updatedWithSuccess ? 1 : 0) };

			break;
		}

		return response;
	}
}