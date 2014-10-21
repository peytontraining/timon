package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.Module;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class ModuleService implements IService<Module> {

    private EntityManager em;

    @Override
    public List<Module> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<Module> query =
            em.createNamedQuery("Module.findAll", Module.class);
        List<Module> modules = query.getResultList();
        em.close();
        return modules;
    }

}
