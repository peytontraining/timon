package vn.enclave.peyton.fusion.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String deploySource;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deployTime;

    private boolean isEditable;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saveTime;

    private String targetVersion;

    // bi-directional many-to-one association to Device
    @OneToMany(mappedBy = "version", cascade = {CascadeType.PERSIST})
    @OrderBy(value = "name DESC")
    private List<Device> devices;

    // bi-directional many-to-one association to Project
    @ManyToOne
    @JoinColumn(name = "idProject")
    private Project project;

    public Version() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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