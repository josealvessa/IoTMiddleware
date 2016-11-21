package br.org.cin.ufpe.IoTMiddleware.server;

import br.org.cin.ufpe.IoTMiddleware.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTMiddleware.pojo.Entity;

public interface ServerInterface {
	void subscribe(Entity entity, SubscriptionListener listener);

	void unregisterQueue(Entity entity);

	void registerQueue(Entity entity);

	void registerCEP(Entity entity);
}