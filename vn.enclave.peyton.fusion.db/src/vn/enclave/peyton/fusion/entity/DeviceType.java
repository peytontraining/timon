package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the device_types database table.
 * 
 */
@Entity
@Table(name="device_types")
@NamedQuery(name="DeviceType.findAll", query="SELECT d FROM DeviceType d")
public class DeviceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to DeviceTemplate
	@OneToMany(mappedBy="deviceType", cascade={CascadeType.PERSIST})
	private List<DeviceTemplate> deviceTemplates;

	//bi-directional many-to-one association to Module
	@ManyToOne
	@JoinColumn(name="idModule")
	private Module module;

	public DeviceType() {
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

	public List<DeviceTemplate> getDeviceTemplates() {
		return this.deviceTemplates;
	}

	public void setDeviceTemplates(List<DeviceTemplate> deviceTemplates) {
		this.deviceTemplates = deviceTemplates;
	}

	public DeviceTemplate addDeviceTemplate(DeviceTemplate deviceTemplate) {
		getDeviceTemplates().add(deviceTemplate);
		deviceTemplate.setDeviceType(this);

		return deviceTemplate;
	}

	public DeviceTemplate removeDeviceTemplate(DeviceTemplate deviceTemplate) {
		getDeviceTemplates().remove(deviceTemplate);
		deviceTemplate.setDeviceType(null);

		return deviceTemplate;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}