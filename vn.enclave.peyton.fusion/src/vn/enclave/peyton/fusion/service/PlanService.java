package vn.enclave.peyton.fusion.service;

import java.util.List;

import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.common.*;
import vn.enclave.peyton.fusion.model.Plan;

public class PlanService extends AbstractService {

	public List<Plan> findAll() {
		em = JPAUtils.getEntityManager();
		TypedQuery<Plan> query = em
				.createNamedQuery("Plan.findAll", Plan.class);
		List<Plan> plans = query.getResultList();
		em.close();
		return plans;
	}
}
