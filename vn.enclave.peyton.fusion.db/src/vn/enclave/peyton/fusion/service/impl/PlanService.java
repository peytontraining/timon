package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class PlanService implements IService<Plan> {

    private EntityManager em;

    @Override
    public List<Plan> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<Plan> query = em
            .createNamedQuery("Plan.findAll", Plan.class);
        List<Plan> plans = query.getResultList();
        em.close();
        return plans;
    }

}