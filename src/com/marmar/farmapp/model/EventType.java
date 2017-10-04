package com.marmar.farmapp.model;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.writers.Writable;

import javafx.scene.image.Image;

@Entity
@Table(name = "EventType")
public class EventType implements Serializable, Writable {

	/*
	 * id_eventtype INT NOT NULL auto_increment, name VARCHAR(100) NOT NULL,
	 * description VARCHAR(200) NOT NULL, img Longblob, PRIMARY
	 * KEY(id_eventtype)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_eventtype")
	private int id_eventtype;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "img")
	private byte[] img;

	public EventType() {

	}

	public EventType(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public int getId_eventtype() {
		return id_eventtype;
	}

	public void setId_eventtype(int id_eventtype) {
		this.id_eventtype = id_eventtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] { id_eventtype, name, description };
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Name", "Description" };
	}
	
	public Image getImage() {
		if (img == null) {
			return null;
		}
		try {
			Image im = ImageManager.byteArraytoFXImage(img);
			return im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new javafx.scene.image.Image("/com/marmar/farmapp/images/empty.png");
	}
}
