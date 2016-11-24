package br.org.cin.RabbitMQRestService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.org.cin.ufpe.middleware.pojo.Metadata;

public class Attributes implements Serializable {

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

	@Override
	public String toString() {
		return "Attributes [name=" + name + ", type=" + type + ", value=" + value + ", metadatas=" + metadatas + "]";
	}

	public Document getDocument() {
		Document document = new Document();
		document.append("name", this.name);
		document.append("type", this.type);
		document.append("value", this.value);

		List<Document> list = new ArrayList<Document>();

		if (metadatas != null && metadatas.size() > 0) {

			for (Metadata metadata : metadatas) {
				list.add(metadata.getDocument());
			}

		}

		document.append("metadatas", list);
		return document;
	}

}
