package br.org.cin.ufpe.middleware.listener;

import br.org.cin.ufpe.middleware.pojo.Entity;

public interface SubscriptionListener {
	void onUpdate(Entity entity);
}
