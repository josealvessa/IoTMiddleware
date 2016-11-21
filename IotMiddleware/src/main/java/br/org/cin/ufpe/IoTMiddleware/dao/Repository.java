package br.org.cin.ufpe.IoTMiddleware.dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.cin.ufpe.IoTMiddleware.naming.model.DocumentInterface;

public class Repository implements RepositoryInterface {

	private MongoClient mongoClient;
	protected MongoDatabase db;

	private String databaseName = "iot_middleware";

	private final String DEFAULT_DATABASE_SERVER = "localhost";
	private final int DEFAULT_DATABASE_PORT = 27017;

	protected String collectionName = "";
	private String searchableField = "";

	public Repository(String collectionName, String serchableField) {

		try {
			this.collectionName = collectionName;
			this.searchableField = serchableField;

			this.mongoClient = new MongoClient(DEFAULT_DATABASE_SERVER, DEFAULT_DATABASE_PORT);
			this.db = mongoClient.getDatabase(databaseName);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public String save(DocumentInterface service) {

		Document document = service.getDocument();
		this.db.getCollection(collectionName).insertOne(document);

		return document.getObjectId("_id").toHexString();
	}

	public boolean removeByField(String value) {

		DeleteResult result = db.getCollection(collectionName).deleteMany(new Document(searchableField, value));
		return result.getDeletedCount() > 0;
	}

	public boolean update(String value, DocumentInterface entity) {

		UpdateResult result = db.getCollection(collectionName).updateOne(new Document(searchableField, value),
				entity.getDocument());

		return result.getModifiedCount() != 0;
	}

	public void clear() {
		db.getCollection(collectionName).drop();
	}

}
