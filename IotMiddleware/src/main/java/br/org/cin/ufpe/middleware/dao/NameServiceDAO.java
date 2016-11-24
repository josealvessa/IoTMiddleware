package br.org.cin.ufpe.middleware.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.cin.ufpe.middleware.naming.model.ServiceAddress;

public class NameServiceDAO extends Repository {

	private static NameServiceDAO dao = null;

	public NameServiceDAO() {
		super("services", "name");
	}

	public ServiceAddress getServiceByName(String serviceName) {

		FindIterable<Document> iterable = db.getCollection(collectionName)
				.find(ServiceAddress.getSearcheableDocument(serviceName));
		return new ServiceAddress(iterable.first());

	}

	public List<ServiceAddress> getAllService() {
		final List<ServiceAddress> addresses = new ArrayList<ServiceAddress>();
		FindIterable<Document> iterable = db.getCollection(collectionName).find();

		iterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				addresses.add(new ServiceAddress(document));
			}
		});

		return addresses;

	}

	public static synchronized NameServiceDAO getDAO() {
		return dao == null ? dao = new NameServiceDAO() : dao;
	}

}