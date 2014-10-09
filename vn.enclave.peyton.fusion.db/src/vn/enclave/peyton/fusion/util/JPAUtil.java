package vn.enclave.peyton.fusion.util;

import javax.persistence.*;

public class JPAUtil {
    
    private static final String 
        PERSISTENCE_UNIT_NAME = "vn.enclave.peyton.fusion.db";
    private static EntityManagerFactory factory;

    static {
        factory = Persistence
                .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
