package br.org.cin.ufpe.IoTCommonsProject.naming;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public interface NamingServiceInterface {
	boolean registerService(ServiceAddress service);

	boolean unregisterService(String serviceName);

	ServiceAddress findService(String findService);

	boolean updateService(ServiceAddress service);
}
