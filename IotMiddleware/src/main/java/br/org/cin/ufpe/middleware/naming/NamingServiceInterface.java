package br.org.cin.ufpe.middleware.naming;

import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;

public interface NamingServiceInterface {
	boolean register(ServiceAddress service);

	boolean unregister(String serviceName);

	ServiceAddress find(String findService);

	boolean update(ServiceAddress service);
}
