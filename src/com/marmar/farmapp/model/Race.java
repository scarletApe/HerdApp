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
@Table(name = "Race")
public class Race implements Serializable, Writable {

	/*
	 * id_race INT NOT NULL auto_increment, name VARCHAR(100) NOT NULL,
	 * description VARCHAR(200), code VARCHAR, img_young Longblob,
	 * img_male_adult Longblob, img_female_adult Longblob, id_animal INT NOT
	 * NULL,
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_race")
	private int id_race;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "code")
	private String code;

	@Column(name = "img_young")
	private byte[] img_young;

	@Column(name = "img_male_adult")
	private byte[] img_male_adult;

	@Column(name = "img_female_adult")
	private byte[] img_female_adult;

	@JoinColumn(name = "id_animal")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Animal animal;

	public Race() {

	}

	public Race(String name, String description, String code, byte[] img_young, byte[] img_male_adult,
			byte[] img_female_adult, Animal animal) {
		super();
		this.name = name;
		this.description = description;
		this.code = code;
		this.img_young = img_young;
		this.img_male_adult = img_male_adult;
		this.img_female_adult = img_female_adult;
		this.animal = animal;
	}

	public int getId_race() {
		return id_race;
	}

	public void setId_race(int id_race) {
		this.id_race = id_race;
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

	public byte[] getImg_male_adult() {
		return img_male_adult;
	}

	public void setImg_male_adult(byte[] img_male_adult) {
		this.img_male_adult = img_male_adult;
	}

	public byte[] getImg_female_adult() {
		return img_female_adult;
	}

	public void setImg_female_adult(byte[] img_female_adult) {
		this.img_female_adult = img_female_adult;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal id_animal) {
		this.animal = id_animal;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public byte[] getImg_young() {
		return img_young;
	}

	public void setImg_young(byte[] img_young) {
		this.img_young = img_young;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Race other = (Race) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Image getImage() {
		if (img_female_adult == null) {
			return null;
		}
		try {
			Image im = ImageManager.byteArraytoFXImage(img_female_adult);
			return im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Image getYoung() {
		if (img_young == null) {
			return null;
		}
		try {
			Image im = ImageManager.byteArraytoFXImage(img_young);
			return im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Image getFemale() {
		if (img_female_adult == null) {
			return null;
		}
		try {
			Image im = ImageManager.byteArraytoFXImage(img_female_adult);
			return im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Image getMale() {
		if (img_male_adult == null) {
			return null;
		}
		try {
			Image im = ImageManager.byteArraytoFXImage(img_male_adult);
			return im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return name + ", " + animal;
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] { id_race, name, description, animal.getName() };
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Name", "Description", "Animal Type" };
	}

}
