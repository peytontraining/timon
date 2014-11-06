package vn.enclave.peyton.fusion.entity;

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
    public static final int DEFAULT_PROJECT_ID = -1;
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean deploymentLocked;

    private boolean gateway;

    private String host;

    private boolean isEditable;

    private String license;

    private String name;

    private String note;

    private int port;

    private String uuid;

    // bi-directional many-to-one association to Plan
    @ManyToOne
    @JoinColumn(name = "idPlan")
    private Plan plan;

    // bi-directional many-to-one association to Version
    @OneToMany(mappedBy = "project")
    @OrderBy(value = "id DESC, name DESC")
    private List<Version> versions;

    public Project() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeploymentLocked() {
        return this.deploymentLocked;
    }

    public void setDeploymentLocked(boolean deploymentLocked) {
        this.deploymentLocked = deploymentLocked;
    }

    public boolean getGateway() {
        return this.gateway;
    }

    public void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public boolean isNewProject() {
        return id == DEFAULT_PROJECT_ID;
    }
}