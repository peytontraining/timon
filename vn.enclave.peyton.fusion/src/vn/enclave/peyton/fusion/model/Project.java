package vn.enclave.peyton.fusion.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the projects database table.
 * 
 */
@Entity
@Table(name = "projects")
@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	// bi-directional many-to-one association to Plan
	@ManyToOne
	@JoinColumn(name = "idPlan")
	private Plan plan;

	// bi-directional many-to-one association to Version
	@OneToMany(mappedBy = "project")
	private List<Version> versions;

	public Project() {
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

	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public List<Version> getVersions() {
		return this.versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public Version addVersion(Version version) {
		getVersions().add(version);
		version.setProject(this);

		return version;
	}

	public Version removeVersion(Version version) {
		getVersions().remove(version);
		version.setProject(null);

		return version;
	}

}