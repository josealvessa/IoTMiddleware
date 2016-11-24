package br.org.cin.ufpe.middleware.server;

import br.org.cin.ufpe.middleware.listener.SubscriptionListener;
import br.org.cin.ufpe.middleware.pojo.Entity;

public interface ServerInterface {
	void subscribe(Entity entity, SubscriptionListener listener);

	void unregisterQueue(Entity entity);

	void registerQueue(Entity entity);

	void registerCEP(Entity entity);
}