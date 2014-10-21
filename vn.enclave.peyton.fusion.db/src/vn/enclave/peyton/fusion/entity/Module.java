package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the modules database table.
 * 
 */
@Entity
@Table(name="modules")
@NamedQuery(name="Module.findAll", query="SELECT m FROM Module m")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to DeviceType
	@OneToMany(mappedBy="module")
	private List<DeviceType> deviceTypes;

	public Module() {
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

	public List<DeviceType> getDeviceTypes() {
		return this.deviceTypes;
	}

	public void setDeviceTypes(List<DeviceType> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	public DeviceType addDeviceType(DeviceType deviceType) {
		getDeviceTypes().add(deviceType);
		deviceType.setModule(this);

		return deviceType;
	}

	public DeviceType removeDeviceType(DeviceType deviceType) {
		getDeviceTypes().remove(deviceType);
		deviceType.setModule(null);

		return deviceType;
	}

}