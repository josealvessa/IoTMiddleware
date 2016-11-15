package br.org.cin.ufpe.IoTCommonsProject.listener;

import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;

public interface SubscriptionListener {
	void onUpdate(Entity entity);
}
