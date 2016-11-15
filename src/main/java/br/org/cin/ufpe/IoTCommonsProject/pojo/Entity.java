package br.org.cin.ufpe.IoTCommonsProject.pojo;

import java.io.Serializable;
import java.util.List;

public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private List<Attributes> attributes;

	public Entity() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Attributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attributes> attributes) {
		this.attributes = attributes;
	}
	
}
