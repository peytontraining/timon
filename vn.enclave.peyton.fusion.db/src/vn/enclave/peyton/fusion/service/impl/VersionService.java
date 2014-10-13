package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class VersionService implements IService<Version> {

    private EntityManager em;

    @Override
    public List<Version> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<Version> query =
            em.createNamedQuery("Version.findAll", Version.class);
        List<Version> version = query.getResultList();
        em.close();
        return version;
    }

    public void updateName(String id, String name) {
        em = JPAUtil.getEntityManager();
        Version version = em.find(Version.class, id);
        em.getTransaction().begin();
        version.setName(name);
        em.getTransaction().commit();
        em.close();
    }

}