package br.org.cin.ufpe.IoTCommonsProject.pojo;

import org.bson.Document;

public class Metadata {
	private String name;
	private String type;
	private String value;

	public Metadata(String name, String type, String value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public Metadata(Document document) {
		this.name = document.getString("name");
		this.type = document.getString("type");
		this.value = document.getString("value");
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

	public Document getDocument() {
		Document document = new Document();
		document.append("name", this.name);
		document.append("type", this.type);
		document.append("value", this.value);
		return document;
	}
}
