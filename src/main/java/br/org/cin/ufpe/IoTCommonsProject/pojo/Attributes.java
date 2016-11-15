package br.org.cin.ufpe.IoTCommonsProject.pojo;

import java.io.Serializable;
import java.util.List;

public class Attributes implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private String value;
	private List<Metadata> metadatas;

	public Attributes(String name, String type, String value, List<Metadata> metadatas) {
		super();
		this.setName(name);
		this.setType(type);
		this.setValue(value);
		this.setMetadas(metadatas);
	}

	public Attributes() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Metadata> getMetadas() {
		return metadatas;
	}

	public void setMetadas(List<Metadata> metadatas) {
		this.metadatas = metadatas;
	}

}
