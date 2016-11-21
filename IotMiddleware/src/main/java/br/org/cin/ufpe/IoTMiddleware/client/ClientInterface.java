package br.org.cin.ufpe.IoTMiddleware.client;

import java.io.IOException;
import java.util.List;

import br.org.cin.ufpe.IoTMiddleware.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTMiddleware.pojo.Entity;

public interface ClientInterface {
	void subscribe(Entity entity, SubscriptionListener listener);

	void subscribeToCEP(Entity entity);

	void unsubscribe(Entity entity);

	Entity register(Entity entity) throws IOException, Throwable;

	List<Entity> discover(Entity entity);

}
