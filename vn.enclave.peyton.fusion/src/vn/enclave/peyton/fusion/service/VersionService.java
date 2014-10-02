package vn.enclave.peyton.fusion.service;

import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.common.*;
import vn.enclave.peyton.fusion.model.Version;

public class VersionService extends AbstractService {

	public Version findByVersionID(String idVersion) {
		em = JPAUtils.getEntityManager();
		TypedQuery<Version> query = em.createNamedQuery(
				"Version.findByVersionID", Version.class);
		query.setParameter("idVersion", idVersion);
		Version version = query.getSingleResult();
		em.close();
		return version;
	}
}
