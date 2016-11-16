package br.org.cin.ufpe.IoTCommonsProject.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.DocumentInterface;

public class Entity implements Serializable, DocumentInterface {

	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private List<Attributes> attributes;

	public Entity() {
		super();
	}

	public Entity(Document document) {

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

	@Override
	public String toString() {
		return "Entity [id=" + id + ", type=" + type + ", attributes=" + attributes + "]";
	}

	// TODO - Use GSON Instead. Not faster but simpler!
	public Document getDocument() {

		Document document = new Document();
		document.append("id", this.id);
		document.append("type", this.type);

		List<Document> array = new ArrayList<Document>();

		if (attributes != null && attributes.size() > 0) {
			for (Attributes attributes : attributes) {
				array.add(attributes.getDocument());
			}

		}

		document.append("attributes", array);

		return document;
	}

}
