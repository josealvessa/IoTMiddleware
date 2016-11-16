package br.org.cin.ufpe.IoTCommonsProject.naming;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public interface NamingServiceInterface {
	boolean register(ServiceAddress service);

	boolean unregister(String serviceName);

	ServiceAddress find(String findService);

	boolean update(ServiceAddress service);
}
