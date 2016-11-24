package br.org.cin.RabbitMQRestService;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

	public MyApplication() {
		// ...

		register(EntityFilteringFeature.class);
		// register(SecurityEntityFilteringFeature.class);
		// register(SelectableEntityFilteringFeature.class);
		   register(JacksonFeature.class);
	}
}