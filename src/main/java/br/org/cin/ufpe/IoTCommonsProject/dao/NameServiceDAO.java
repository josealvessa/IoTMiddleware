package br.org.cin.ufpe.IoTCommonsProject.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public class NameServiceDAO implements NameServiceDaoInterface {

	private MongoClient mongoClient;
	private MongoDatabase db;

	private String databaseName = "iot_middleware";

	private final String DEFAULT_DATABASE_SERVER = "localhost";
	private final int DEFAULT_DATABASE_PORT = 27017;

	private final String DEFAULT_COLLECTION = "services";

	public NameServiceDAO() {

		try {
			this.mongoClient = new MongoClient(DEFAULT_DATABASE_SERVER, DEFAULT_DATABASE_PORT);
			this.db = mongoClient.getDatabase(databaseName);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public boolean saveService(ServiceAddress service) {

		Document document = service.getDocument();
		this.db.getCollection(DEFAULT_COLLECTION).insertOne(document);

		return document.get("_id") != null;
	}

	public ServiceAddress getServiceByName(String serviceName) {

		FindIterable<Document> iterable = db.getCollection(DEFAULT_COLLECTION)
				.find(ServiceAddress.getSearcheableDocument(serviceName));
		return new ServiceAddress(iterable.first());

	}

	public List<ServiceAddress> getAllService() {
		final List<ServiceAddress> addresses = new ArrayList<ServiceAddress>();
		FindIterable<Document> iterable = db.getCollection(DEFAULT_COLLECTION).find();

		iterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				addresses.add(new ServiceAddress(document));
			}
		});

		return addresses;

	}

	public boolean removeServiceByName(String serviceName) {
		DeleteResult result = db.getCollection(DEFAULT_COLLECTION)
				.deleteMany(ServiceAddress.getSearcheableDocument(serviceName));
		return result.getDeletedCount() > 0;
	}

	public boolean updateService(ServiceAddress service) {

		UpdateResult result = db.getCollection(DEFAULT_COLLECTION)
				.updateOne(ServiceAddress.getSearcheableDocument(service.getName()), service.getDocument());

		return result.getModifiedCount() != 0;
	}

	public void clear() {
		db.getCollection(DEFAULT_COLLECTION).drop();
	}

}