package br.org.cin.ufpe.IoTCommonsProject.dao;

public class NamingServiceFactory {
	private static NameServiceDaoInterface dao = null;

	public static synchronized NameServiceDaoInterface getDAO() {
		return dao == null ? dao = new NameServiceDAO() : dao;
	}
}