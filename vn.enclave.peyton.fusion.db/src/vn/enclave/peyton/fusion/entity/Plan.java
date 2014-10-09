package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the plans database table.
 * 
 */
@Entity
@Table(name="plans")
@NamedQuery(name="Plan.findAll", query="SELECT p FROM Plan p")
public class Plan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="plan")
	private List<Project> projects;

	public Plan() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setPlan(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setPlan(null);

		return project;
	}

}