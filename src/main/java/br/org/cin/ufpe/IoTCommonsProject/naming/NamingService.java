package br.org.cin.ufpe.IoTCommonsProject.naming;

import br.org.cin.ufpe.IoTCommonsProject.dao.NamingServiceFactory;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public class NamingService implements NamingServiceInterface {

	public boolean registerService(ServiceAddress service) {
		return NamingServiceFactory.getDAO().saveService(service);
	}

	public boolean unregisterService(String serviceName) {
		return NamingServiceFactory.getDAO().removeServiceByName(serviceName);
	}

	public ServiceAddress findService(String serviceName) {
		return NamingServiceFactory.getDAO().getServiceByName(serviceName);
	}

	public boolean updateService(ServiceAddress service) {
		return NamingServiceFactory.getDAO().updateService(service);
	}

}
