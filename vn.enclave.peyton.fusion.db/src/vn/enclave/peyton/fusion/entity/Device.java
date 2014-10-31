package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the devices database table.
 * 
 */
@Entity
@Table(name = "devices")
@NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String appModule;

    private String deviceType;

    private String manufacturer;

    private String name;

    private String physicalLocation;

    private String modelNumber;

    private String notes;

    @Column(name = "version")
    private String versionDevice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    // bi-directional many-to-one association to Version
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "idVersion")
    private Version version;

    // uni-directional many-to-one association to Icon
    @ManyToOne
    @JoinColumn(name = "idIcon")
    private Icon icon;

    // bi-directional many-to-one association to Version
    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST})
    private List<Property> properties;

    public Device() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppModule() {
        return this.appModule;
    }

    public void setAppModule(String appModule) {
        this.appModule = appModule;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalLocation() {
        return this.physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getVersionDevice() {
        return versionDevice;
    }

    public void setVersionDevice(String versionDevice) {
        this.versionDevice = versionDevice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Property addProperty(Property property) {
        getProperties().add(property);
        property.setDevice(this);

        return property;
    }

    public Property removeProperty(Property property) {
        getProperties().remove(property);
        property.setDevice(null);

        return property;
    }
}