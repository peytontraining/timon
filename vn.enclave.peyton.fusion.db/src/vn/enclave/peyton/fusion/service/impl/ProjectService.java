package vn.enclave.peyton.fusion.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.service.IService;
import vn.enclave.peyton.fusion.util.JPAUtil;

public class ProjectService implements IService<Project> {

    private EntityManager em;

    @Override
    public List<Project> getAll() {
        em = JPAUtil.getEntityManager();
        TypedQuery<Project> query = em
            .createNamedQuery("Project.findAll", Project.class);
        List<Project> projects = query.getResultList();
        em.close();
        return projects;
    }

    public Project add(Project project) {
        em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        project = em.merge(project);
        em.getTransaction().commit();
        em.close();
        return project;
    }
}
