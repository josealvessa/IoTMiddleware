package br.org.cin.ufpe.IoTMiddleware.dao;

import br.org.cin.ufpe.IoTMiddleware.naming.model.DocumentInterface;

public interface RepositoryInterface {

	String save(DocumentInterface service);

	boolean update(String value, DocumentInterface entity);

	boolean removeByField(String value);

	void clear();
}
