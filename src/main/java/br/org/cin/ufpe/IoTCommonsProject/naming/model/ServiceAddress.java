package br.org.cin.ufpe.IoTCommonsProject.naming.model;

import java.util.Dictionary;

import org.bson.Document;

public class ServiceAddress {

	private String name;
	private String type;
	private String host;
	private int port;

	private String user;
	private String password;

	private Dictionary<String, String> extras;

	public ServiceAddress() {
		super();
	}

	public ServiceAddress(Document document) {
		this.host = document.getString("host");
		this.type = document.getString("type");
		this.name = document.getString("name");
		this.port = document.getInteger("port");
		this.user = document.getString("user");
		this.password = document.getString("password");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Document getDocument() {
		Document document = new Document();
		document.append("host", this.getHost());
		document.append("name", this.getName());
		document.append("port", this.getPort());
		document.append("type", this.getType());
		document.append("user", this.getUser());
		document.append("password", this.getPassword());
		return document;
	}

	public static Document getSearcheableDocument(String serviceName) {
		return new Document("name", serviceName);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Dictionary<String, String> getExtras() {
		return extras;
	}

	public void setExtras(Dictionary<String, String> extras) {
		this.extras = extras;
	}

}
