package br.org.cin.ufpe.IoTMiddleware.listener;

import br.org.cin.ufpe.IoTMiddleware.pojo.Entity;

public interface SubscriptionListener {
	void onUpdate(Entity entity);
}
