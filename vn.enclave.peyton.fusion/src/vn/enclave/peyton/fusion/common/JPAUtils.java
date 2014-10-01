package vn.enclave.peyton.fusion.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {

	private static final String PERSISTENCE_UNIT_NAME = "vn.enclave.peyton.fusion";

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
