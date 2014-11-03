package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the property_templates database table.
 * 
 */
@Entity
@Table(name = "property_templates")
@NamedQuery(name = "PropertyTemplate.findAll", query = "SELECT p FROM PropertyTemplate p")
public class PropertyTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private boolean isMandatory;

    private String name;

    private String value;

    // bi-directional many-to-one association to DeviceTemplate1
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "idDeviceTemplate")
    private DeviceTemplate deviceTemplate;

    public PropertyTemplate() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMandatory() {
        return this.isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DeviceTemplate getDeviceTemplate() {
        return this.deviceTemplate;
    }

    public void setDeviceTemplate(DeviceTemplate deviceTemplate) {
        this.deviceTemplate = deviceTemplate;
    }

}