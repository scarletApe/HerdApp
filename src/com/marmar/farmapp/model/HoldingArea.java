package com.marmar.farmapp.model;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.writers.Writable;

import javafx.scene.image.Image;

@Entity
@Table(name = "Holding_Area")
public class HoldingArea implements Serializable, Writable {

	/*
	 * id_hold INT NOT NULL auto_increment, id_ranch INT, name VARCHAR,
	 * description VARCHAR, img Longblob, area REAL, au_capacity REAL,
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_hold")
	private int id_hold;

	@JoinColumn(name = "id_ranch")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ranch ranch;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "img")
	private byte[] img;

	@Column(name = "area")
	private double area;

	@Column(name = "au_capacity")
	private double au_capacity;

	public HoldingArea() {

	}

	public HoldingArea(Ranch ranch, String name, String description, byte[] img, double area, double au_capacity) {
		super();
		this.ranch = ranch;
		this.name = name;
		this.description = description;
		this.img = img;
		this.area = area;
		this.au_capacity = au_capacity;
	}

	public int getId_hold() {
		return id_hold;
	}

	public void setId_hold(int id_hold) {
		this.id_hold = id_hold;
	}

	public Ranch getRanch() {
		return ranch;
	}

	public void setRanch(Ranch ranch) {
		this.ranch = ranch;
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

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getAu_capacity() {
		return au_capacity;
	}

	public void setAu_capacity(double au_capacity) {
		this.au_capacity = au_capacity;
	}

	@Override
	public String toString() {
		return name;
	}

	/** TODO*/
	@Override
	public Object[] getAsArray() {
		return new Object[] {};
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Ear Tag", "Gender", "Name", "Status", "Color", "Description", "Breed 1", "Breed 2",
				"Date Birth", "Date Death", "Farm", "Mother", "Father" };
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
