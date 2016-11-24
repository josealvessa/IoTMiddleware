package br.org.cin.RabbitMQRestService;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;


@Path("/sensors")
public class ServiceController {

	@Autowired
	private Service service;

	@POST
	@Path("/discover")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response discover(Entity entity) {
		return Response.ok().entity(service.getController().discover(null)).build();
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(Entity entity) throws IOException, Throwable {
		return Response.ok().entity(service.getController().register(null)).build();
	}

	@POST
	@Path("subscribecep")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response subscribeCEP(Entity arg0) {
		return null;
	}

	@POST
	@Path("/unsubscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response unsubscribe(Entity arg0) {
		return null;
	}

	@GET
	@Path("/getMsg")
	public String getMsg() {
		return "Hello World !! - Jersey 2";
	}
}