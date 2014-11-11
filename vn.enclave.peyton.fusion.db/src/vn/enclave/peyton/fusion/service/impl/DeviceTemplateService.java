package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class DeviceTemplateService implements IService<DeviceTemplate> {

    private EntityManager em;

    @Override
    public List<DeviceTemplate> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<DeviceTemplate> query = em.createNamedQuery("Device.findAll", DeviceTemplate.class);
        List<DeviceTemplate> deviceTemplates = query.getResultList();
        em.close();
        return deviceTemplates;
    }

    public DeviceTemplate update(DeviceTemplate modifiedDeviceTemplate) {
        em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        modifiedDeviceTemplate = em.merge(modifiedDeviceTemplate);
        em.getTransaction().commit();
        em.close();
        return modifiedDeviceTemplate;
    }
}
