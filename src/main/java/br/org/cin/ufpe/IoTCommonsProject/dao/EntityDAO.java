package br.org.cin.ufpe.IoTCommonsProject.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.cin.ufpe.IoTCommonsProject.parser.DocumentParser;
import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;

public class EntityDAO extends Repository {

	private static EntityDAO dao = null;

	public EntityDAO() {
		super("entity", "id");
	}

	public static synchronized EntityDAO getDAO() {
		return dao == null ? dao = new EntityDAO() : dao;
	}

	public Entity find(Entity entity) {
		FindIterable<Document> iterable = db.getCollection(collectionName).find(entity.getSeachableDocument());
		return DocumentParser.parseEntity(iterable.first());
	}

	public List<Entity> discover(Entity entity) {
		final List<Entity> entities = new ArrayList<Entity>();
		FindIterable<Document> iterable = db.getCollection(collectionName).find();

		iterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				entities.add(DocumentParser.parseEntity(document));
			}
		});

		return entities;
	}

}