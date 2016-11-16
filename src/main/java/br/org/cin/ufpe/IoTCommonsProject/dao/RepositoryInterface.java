package br.org.cin.ufpe.IoTCommonsProject.dao;

import br.org.cin.ufpe.IoTCommonsProject.naming.model.DocumentInterface;

public interface RepositoryInterface {

	boolean save(DocumentInterface service);

	boolean update(String value, DocumentInterface entity);

	boolean removeByField(String value);

	void clear();
}
