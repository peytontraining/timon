package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the device_templates database table.
 * 
 */
@Entity
@Table(name = "device_templates")
@NamedQuery(name = "DeviceTemplate.findAll", query = "SELECT d FROM DeviceTemplate d")
public class DeviceTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "certified")
    private boolean isCertified;

    private String deviceDriver;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    private String manufacturer;

    private String modelNumber;

    private String name;

    private String notes;

    private String version;

    // bi-directional many-to-one association to DeviceType
    @ManyToOne
    @JoinColumn(name = "idType")
    private DeviceType deviceType;

    // uni-directional many-to-one association to Icon
    @ManyToOne
    @JoinColumn(name = "idIcon")
    private Icon icon;

    // bi-directional many-to-one association to PropertyTemplate
    @OneToMany(mappedBy = "deviceTemplate", cascade = {CascadeType.PERSIST})
    private List<PropertyTemplate> propertyTemplates;

    public DeviceTemplate() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCertified() {
        return this.isCertified;
    }

    public void setCertified(boolean isCertified) {
        this.isCertified = isCertified;
    }

    public String getDeviceDriver() {
        return this.deviceDriver;
    }

    public void setDeviceDriver(String deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public List<PropertyTemplate> getPropertyTemplates() {
        return this.propertyTemplates;
    }

    public void setPropertyTemplates(List<PropertyTemplate> propertyTemplates) {
        this.propertyTemplates = propertyTemplates;
    }

    public PropertyTemplate addPropertyTemplate(PropertyTemplate propertyTemplate) {
        getPropertyTemplates().add(propertyTemplate);
        propertyTemplate.setDeviceTemplate(this);

        return propertyTemplate;
    }

    public PropertyTemplate removePropertyTemplate(PropertyTemplate propertyTemplate) {
        getPropertyTemplates().remove(propertyTemplate);
        propertyTemplate.setDeviceTemplate(null);

        return propertyTemplate;
    }
}