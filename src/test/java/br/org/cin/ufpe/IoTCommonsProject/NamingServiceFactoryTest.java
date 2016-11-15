package br.org.cin.ufpe.IoTCommonsProject;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.cin.ufpe.IoTCommonsProject.dao.NameServiceDaoInterface;
import br.org.cin.ufpe.IoTCommonsProject.dao.NamingServiceFactory;
import br.org.cin.ufpe.IoTCommonsProject.naming.model.ServiceAddress;

public class NamingServiceFactoryTest {

	@Before
	public void setup() {
		NamingServiceFactory.getDAO().clear();
	}

	@Test
	public void testAddService() {

		NameServiceDaoInterface dao = NamingServiceFactory.getDAO();

		ServiceAddress address = new ServiceAddress();
		address.setHost("localhost");
		address.setName("producer");
		address.setPort(4321);
		address.setType("sensor");

		boolean wasSavedWithSuccess = dao.saveService(address);
		Assert.assertEquals(true, wasSavedWithSuccess);

		ServiceAddress serviceAddressFromDatabase = dao.getServiceByName("producer");

		Assert.assertEquals("localhost", serviceAddressFromDatabase.getHost());
		Assert.assertEquals("producer", serviceAddressFromDatabase.getName());
		Assert.assertEquals(4321, serviceAddressFromDatabase.getPort());
		Assert.assertEquals("sensor", serviceAddressFromDatabase.getType());
	}

	@Test
	public void testClear() {

		NameServiceDaoInterface dao = NamingServiceFactory.getDAO();

		ServiceAddress address = new ServiceAddress();
		address.setHost("localhost");
		address.setName("producer");
		address.setPort(4321);
		address.setType("sensor");

		boolean wasSavedWithSuccess = dao.saveService(address);
		Assert.assertEquals(true, wasSavedWithSuccess);

		dao.clear();

		List<ServiceAddress> services = dao.getAllService();

		Assert.assertEquals(0, services.size());
	}

	@Test
	public void testLists() {

		NameServiceDaoInterface dao = NamingServiceFactory.getDAO();

		ServiceAddress address = new ServiceAddress();
		address.setHost("localhost");
		address.setName("producer");
		address.setPort(4321);
		address.setType("sensor");

		boolean wasSavedWithSuccess = dao.saveService(address);
		Assert.assertEquals(true, wasSavedWithSuccess);

		ServiceAddress address2 = new ServiceAddress();
		address2.setHost("localhost2");
		address2.setName("producer2");
		address2.setPort(43212);
		address2.setType("sensor2");

		boolean wasSavedWithSuccess2 = dao.saveService(address);
		Assert.assertEquals(true, wasSavedWithSuccess2);

		List<ServiceAddress> serviceAddressFromDatabase = dao.getAllService();

		Assert.assertEquals(2, serviceAddressFromDatabase.size());

	}

	@AfterClass
	public static void tearDown() {
		NamingServiceFactory.getDAO().clear();
	}
}
