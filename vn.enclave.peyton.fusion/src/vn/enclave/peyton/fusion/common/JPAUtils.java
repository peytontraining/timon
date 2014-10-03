package vn.enclave.peyton.fusion.common;

import javax.persistence.*;

public class JPAUtils {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence
				.createEntityManagerFactory(Constant.PERSISTENCE_UNIT_NAME);
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
