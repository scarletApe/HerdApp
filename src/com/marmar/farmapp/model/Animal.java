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
@Table(name = "Animal")
public class Animal implements Serializable, Writable {
	/*
	 * id_animal INT NOT NULL auto_increment, name VARCHAR(100) NOT NULL,
	 * description VARCHAR(200), img Longblob, to_juvenile INT, to_adult INT,
	 * au_infant DECIMAL, au_juvenile DECIMAL, au_adult_female DECIMAL,
	 * au_adult_male DECIMAL,
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_animal")
	private int id_animal;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "img")
	private byte[] img;

	@Column(name = "to_juvenile")
	private int to_juvenile;

	@Column(name = "to_adult")
	private int to_adult;

	@Column(name = "au_infant")
	private double au_infant;

	@Column(name = "au_juvenile")
	private double au_juvenile;

	@Column(name = "au_adult_female")
	private double au_adult_female;

	@Column(name = "au_adult_male")
	private double au_adult_male;

	public Animal() {
	}

	public Animal(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Animal(String name, String description, byte[] img, int to_juvenile, int to_adult, double au_infant,
			double au_juvenile, double au_adult_female, double au_adult_male) {
		super();
		this.name = name;
		this.description = description;
		this.img = img;
		this.to_juvenile = to_juvenile;
		this.to_adult = to_adult;
		this.au_infant = au_infant;
		this.au_juvenile = au_juvenile;
		this.au_adult_female = au_adult_female;
		this.au_adult_male = au_adult_male;
	}

	public int getId_animal() {
		return id_animal;
	}

	public void setId_animal(int id_animal) {
		this.id_animal = id_animal;
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

	public int getTo_juvenile() {
		return to_juvenile;
	}

	public void setTo_juvenile(int to_juvenile) {
		this.to_juvenile = to_juvenile;
	}

	public int getTo_adult() {
		return to_adult;
	}

	public void setTo_adult(int to_adult) {
		this.to_adult = to_adult;
	}

	public double getAu_infant() {
		return au_infant;
	}

	public void setAu_infant(double au_infant) {
		this.au_infant = au_infant;
	}

	public double getAu_juvenile() {
		return au_juvenile;
	}

	public void setAu_juvenile(double au_juvenile) {
		this.au_juvenile = au_juvenile;
	}

	public double getAu_adult_female() {
		return au_adult_female;
	}

	public void setAu_adult_female(double au_adult_female) {
		this.au_adult_female = au_adult_female;
	}

	public double getAu_adult_male() {
		return au_adult_male;
	}

	public void setAu_adult_male(double au_adult_male) {
		this.au_adult_male = au_adult_male;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id_animal;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id_animal != other.id_animal)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
		return null;
	}

	@Override
	public String toString() {
		return name.toUpperCase();
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] { id_animal, name, description };
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Name", "Description" };
	}
}
