package vn.enclave.peyton.fusion.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the versions database table.
 * 
 */
@Entity
@Table(name = "versions")
@NamedQuery(name = "Version.findAll", query = "SELECT v FROM Version v")
public class Version implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String deploySource;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deployTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date saveTime;

	private String targetVersion;

	// bi-directional many-to-one association to Device
	@OneToMany(mappedBy = "version")
	private List<Device> devices;

	// bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name = "idProject")
	private Project project;

	public Version() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeploySource() {
		return this.deploySource;
	}

	public void setDeploySource(String deploySource) {
		this.deploySource = deploySource;
	}

	public Date getDeployTime() {
		return this.deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public Date getSaveTime() {
		return this.saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public String getTargetVersion() {
		return this.targetVersion;
	}

	public void setTargetVersion(String targetVersion) {
		this.targetVersion = targetVersion;
	}

	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Device addDevice(Device device) {
		getDevices().add(device);
		device.setVersion(this);

		return device;
	}

	public Device removeDevice(Device device) {
		getDevices().remove(device);
		device.setVersion(null);

		return device;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}