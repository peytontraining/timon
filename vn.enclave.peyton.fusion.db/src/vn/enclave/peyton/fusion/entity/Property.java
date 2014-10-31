package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the properties database table.
 * 
 */
@Entity
@Table(name = "properties")
@NamedQuery(name = "Property.findAll", query = "SELECT p FROM Property p")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private boolean isMandatory;

    private String name;

    private String value;

    // bi-directional many-to-one association to Version
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "idDevice")
    private Device device;

    public Property() {
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}