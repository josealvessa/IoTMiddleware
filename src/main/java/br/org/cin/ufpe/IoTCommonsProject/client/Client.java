package br.org.cin.ufpe.IoTCommonsProject.client;

import br.org.cin.ufpe.IoTCommonsProject.listener.SubscriptionListener;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;

public class Client implements ClientInterface {

	public void subscribe(Entity entity, SubscriptionListener listener) {
		// RabbitMQ	
	}

	public void unsubscribe(Entity entity) {
		// RabbitMQ	
	}

	public void register(Entity entity) {
		// Service
	}

	public void discover(Entity entity) {
		// Service
	}

	public void subscribeToCEP(Entity entity) {
		// RabbitMQ
	}

}
