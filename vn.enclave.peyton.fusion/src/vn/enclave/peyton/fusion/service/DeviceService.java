package vn.enclave.peyton.fusion.service;

import java.util.List;

import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.common.AbstractService;
import vn.enclave.peyton.fusion.common.JPAUtils;

public class DeviceService extends AbstractService {

	public List<Object[]> findAllByVersionID(String versionID) {
		em = JPAUtils.getEntityManager();
		TypedQuery<Object[]> query = em.createNamedQuery(
				"Device.findAllByVersionID", Object[].class);
		query.setParameter("versionID", versionID);
		List<Object[]> devices = query.getResultList();
		em.close();
		return devices;
	}

}
