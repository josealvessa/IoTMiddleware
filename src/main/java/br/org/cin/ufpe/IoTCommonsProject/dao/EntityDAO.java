package br.org.cin.ufpe.IoTCommonsProject.dao;

import br.org.cin.ufpe.IoTCommonsProject.pojo.Entity;

public class EntityDAO extends Repository {

	private static EntityDAO dao = null;

	public EntityDAO() {
		super("entity", "id");
	}

	public static synchronized EntityDAO getDAO() {
		return dao == null ? dao = new EntityDAO() : dao;
	}

	public Entity find(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}

}