package br.org.cin.ufpe.IoTCommonsProject.server;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;

public interface ServerInterface {
	void subscribe(Entity entity, SubscriptionListener listener);

	void unregisterQueue(Entity entity);

	void registerQueue(Entity entity);

	void registerCEP(Entity entity);
}