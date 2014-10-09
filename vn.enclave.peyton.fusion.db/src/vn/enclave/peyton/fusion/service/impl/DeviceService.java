package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class DeviceService implements IService<Device> {

    private EntityManager em;

    public List<Device> findAllByID(String versionID) {
        em = JPAUtil.getEntityManager();
        TypedQuery<Device> query = 
            em.createNamedQuery("Device.findAllByID", Device.class);
        query.setParameter("versionID", versionID);
        List<Device> devices = query.getResultList();
        em.close();
        return devices;
    }

    @Override
    public List<Device> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<Device> query = 
            em.createNamedQuery("Device.findAll", Device.class);
        List<Device> devices = query.getResultList();
        em.close();
        return devices;
    }
}
