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
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    // If true, gateway uses UUID.
    // If false, gatewate uses Host/Port.
    private boolean gateway;
    
    private String host;
    
    private String name;
    
    private String note;
    
    private String port;
    
    private String uuid;
    
    // bi-directional many-to-one association to Plan
    @ManyToOne(cascade={CascadeType.PERSIST})
    @JoinColumn(name = "idPlan")
    private Plan plan;
    
    // bi-directional many-to-one association to Version
    @OneToMany(mappedBy = "project", cascade={CascadeType.PERSIST})
    @OrderBy(value = "name DESC")
    private List<Version> versions;
    
    public Project() {
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public String getPort() {
        return this.port;
    }
    
    public void setPort(String port) {
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
    
}