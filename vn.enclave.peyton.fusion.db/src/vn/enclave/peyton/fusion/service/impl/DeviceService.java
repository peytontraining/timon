package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class DeviceService implements IService<Device> {

    private EntityManager em;

    @Override
    public List<Device> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<Device> query =
            em.createNamedQuery("Device.findAll", Device.class);
        List<Device> devices = query.getResultList();
        em.close();
        return devices;
    }

    public Device insert(Device newDevice) {
        em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        newDevice = em.merge(newDevice);
        em.getTransaction().commit();
        em.close();
        return newDevice;
    }

    public void update(Device modifiedDevice) {
        em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(modifiedDevice);
        em.getTransaction().commit();
        em.close();
    }
}