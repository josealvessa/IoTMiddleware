package br.org.cin.ufpe.middleware.client;

import java.io.IOException;
import java.util.List;

import br.org.cin.ufpe.middleware.listener.SubscriptionListener;
import br.org.cin.ufpe.middleware.pojo.Entity;

public interface ClientInterface {
	void subscribe(SubscriptionListener listener);

	void publish(Entity entity);

	Entity register(Entity entity) throws IOException, Throwable;

	List<Entity> discover(Entity entity);

}
