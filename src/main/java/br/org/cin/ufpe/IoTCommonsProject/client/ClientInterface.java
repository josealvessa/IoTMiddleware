package br.org.cin.ufpe.IoTCommonsProject.client;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;

public interface ClientInterface {
	void subscribe(Entity entity, SubscriptionListener listener);
	
	void subscribeToCEP(Entity entity);

	void unsubscribe(Entity entity);

	void register(Entity entity);

	void discover(Entity entity);
}
