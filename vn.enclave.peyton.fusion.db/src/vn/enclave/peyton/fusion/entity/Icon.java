package vn.enclave.peyton.fusion.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the icons database table.
 * 
 */
@Entity
@Table(name="icons")
@NamedQuery(name="Icon.findAll", query="SELECT i FROM Icon i")
public class Icon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String imageFilePath;

	private String pluginId;

	public Icon() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageFilePath() {
		return this.imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getPluginId() {
		return this.pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

}