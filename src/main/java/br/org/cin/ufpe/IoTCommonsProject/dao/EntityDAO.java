package br.org.cin.ufpe.IoTCommonsProject.dao;

public class EntityDAO extends Repository {

	private static EntityDAO dao = null;

	public EntityDAO() {
		super("entity", "id");
	}

	public static synchronized EntityDAO getDAO() {
		return dao == null ? dao = new EntityDAO() : dao;
	}

}