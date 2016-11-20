package br.org.cin.ufpe.IoTCommonsProject.parser;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.org.cin.ufpe.IoTCommonsProject.pojo.Attributes;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Metadata;

public class DocumentParser {

	public static Entity parseEntity(Document document) {

		List<Document> attributesDocument = document.get("attributes", List.class);
		List<Attributes> attributes = new ArrayList<Attributes>();

		if (attributesDocument != null && !attributesDocument.isEmpty()) {
			for (Document attributeDocument : attributesDocument) {
				attributes.add(parseAttributes(attributeDocument));
			}
		}

		return new Entity(document.getObjectId("_id").toHexString(), document.getString("type"), attributes);
	}

	private static Attributes parseAttributes(Document document) {

		List<Document> metadataList = document.get("metadatas", List.class);
		List<Metadata> metadata = new ArrayList<Metadata>();

		if (metadataList != null && !metadataList.isEmpty()) {
			for (Document attributeDocument : metadataList) {
				metadata.add(parseMetadata(attributeDocument));
			}
		}

		return new Attributes(document.getString("name"), document.getString("type"), document.getString("value"),
				metadata);
	}

	private static Metadata parseMetadata(Document document) {
		return new Metadata(document.getString("name"), document.getString("type"), document.getString("value"));
	}

}
