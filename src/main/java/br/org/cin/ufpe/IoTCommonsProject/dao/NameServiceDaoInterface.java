package br.org.cin.ufpe.IoTCommonsProject.dao;

import java.util.List;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public interface NameServiceDaoInterface {
	boolean saveService(ServiceAddress service);

	ServiceAddress getServiceByName(String serviceName);

	List<ServiceAddress> getAllService();

	boolean removeServiceByName(String serviceName);

	void clear();

	boolean updateService(ServiceAddress service);
}
