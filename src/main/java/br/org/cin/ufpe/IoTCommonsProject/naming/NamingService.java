package br.org.cin.ufpe.IoTCommonsProject.naming;

import br.org.cin.ufpe.IoTCommonsProject.dao.NameServiceDAO;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public class NamingService implements NamingServiceInterface {

	public boolean registerService(ServiceAddress service) {
		return NameServiceDAO.getDAO().save(service);
	}

	public boolean unregisterService(String serviceName) {
		return NameServiceDAO.getDAO().removeByField(serviceName);
	}

	public ServiceAddress findService(String serviceName) {
		return NameServiceDAO.getDAO().getServiceByName(serviceName);
	}

	public boolean updateService(ServiceAddress service) {
		return NameServiceDAO.getDAO().update(service.getName(), service);
	}

}