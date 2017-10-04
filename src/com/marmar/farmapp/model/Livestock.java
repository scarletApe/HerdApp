package com.marmar.farmapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Type;

import com.marmar.farmapp.util.ResourceManager;
import com.marmar.farmapp.util.writers.Writable;

@Entity
@Table(name = "Livestock")
public class Livestock implements Serializable, Writable {

	/*
	 * id_race_1 INT NOT NULL, id_race_2 INT, id_ranch INT NOT NULL, id_mother
	 * INT, id_father INT, ear_ring VARCHAR(100), gender INT, name VARCHAR(100),
	 * status VARCHAR(100), date_birth Date, date_death Date, cause_death
	 * VARCHAR, img Longblob, img_dead Longblob, color VARCHAR(100) ,
	 * description VARCHAR(300), active INT(1), img_iron Longblob, branded
	 * INT(1), weight DECIMAL,
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_livestock")
	private int id_livestock;

	@JoinColumn(name = "id_race_1")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Race id_race_1;

	@JoinColumn(name = "id_race_2")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Race id_race_2;

	@JoinColumn(name = "id_ranch")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ranch ranch;

	@JoinColumn(name = "id_mother")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Livestock id_mother;

	@JoinColumn(name = "id_father")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Livestock id_father;

	@Column(name = "ear_ring")
	private String ear_ring;

	@Column(name = "gender")
	private int gender;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private String status;

	@Column(name = "date_birth")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_birth;

	@Column(name = "date_death")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_death;

	@Column(name = "img")
	private byte[] img;

	@Column(name = "img_dead")
	private byte[] img_dead;

	@Column(name = "color")
	private String color;

	@Column(name = "description")
	private String description;

	@Type(type = "true_false")
	@Column(name = "active")
	private boolean active;

	@Column(name = "img_iron")
	private byte[] img_iron;

	@Type(type = "true_false")
	@Column(name = "branded")
	private boolean branded;

	@Column(name = "weight")
	private double weight;

	@Column(name = "cause_death")
	private String cause_death;

	public Livestock() {

	}

	public Livestock(Race race_1, Race race_2, Ranch ranch, int gender, Date date_birth, String color) {
		this();
		this.id_race_1 = race_1;
		this.id_race_2 = race_2;
		this.ranch = ranch;
		this.gender = gender;
		this.date_birth = date_birth;
		this.color = color;
	}

	public int getId_livestock() {
		return id_livestock;
	}

	public void setId_livestock(int id_livestock) {
		this.id_livestock = id_livestock;
	}

	public Ranch getRanch() {
		return ranch;
	}

	public void setRanch(Ranch ranch) {
		this.ranch = ranch;
	}

	public String getEar_ring() {
		return ear_ring;
	}

	public void setEar_ring(String ear_ring) {
		this.ear_ring = ear_ring;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate_birth() {
		return date_birth;
	}

	public void setDate_birth(Date date_birth) {
		this.date_birth = date_birth;
	}

	public Date getDate_death() {
		return date_death;
	}

	public void setDate_death(Date date_death) {
		this.date_death = date_death;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public byte[] getImg_dead() {
		return img_dead;
	}

	public void setImg_dead(byte[] img_dead) {
		this.img_dead = img_dead;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Race getId_race_1() {
		return id_race_1;
	}

	public void setId_race_1(Race id_race_1) {
		this.id_race_1 = id_race_1;
	}

	public Race getId_race_2() {
		return id_race_2;
	}

	public void setId_race_2(Race id_race_2) {
		this.id_race_2 = id_race_2;
	}

	public Livestock getId_mother() {
		return id_mother;
	}

	public void setId_mother(Livestock id_mother) {
		this.id_mother = id_mother;
	}

	public Livestock getId_father() {
		return id_father;
	}

	public void setId_father(Livestock id_father) {
		this.id_father = id_father;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public byte[] getImg_iron() {
		return img_iron;
	}

	public void setImg_iron(byte[] img_iron) {
		this.img_iron = img_iron;
	}

	public boolean isBranded() {
		return branded;
	}

	public void setBranded(boolean branded) {
		this.branded = branded;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getCause_death() {
		return cause_death;
	}

	public void setCause_death(String cause_death) {
		this.cause_death = cause_death;
	}

	@SuppressWarnings("deprecation")
	public int getAge() {
		if (date_birth == null) {
			return 0;
		}
		if (date_death != null) {
			Date actual = date_death;

			int actual_months = (actual.getMonth() + 1) + (actual.getYear() * 12);
			int d_months = (date_birth.getMonth() + 1) + (date_birth.getYear() * 12);

			return actual_months - d_months;
		}
		// no esta muerto
		Date actual = new Date();

		int actual_months = (actual.getMonth() + 1) + (actual.getYear() * 12);
		int d_months = (date_birth.getMonth() + 1) + (date_birth.getYear() * 12);

		return actual_months - d_months;
	}

	public String getSex() {
		// 0 = male
		// 1 = female
		switch (gender) {
		case 0:
			ResourceManager.localizer.getMessages().getString("label.gender.male");
//			return "Male";
		case 1:
			return ResourceManager.localizer.getMessages().getString("label.gender.female");
//			return "Female";
		}
		return "undefined";

	}

	public double getAnimalUnit() {
		// first figure out the age of this animal
		int months = getAge();

		// get the type of animal
		Animal a = id_race_1.getAnimal();

		if (months >= a.getTo_adult()) {
			if (getGender() == 0) {
				// male
				return a.getAu_adult_male();
			} else if (getGender() == 1) {
				// female
				return a.getAu_adult_female();
			}
		}
		if (months >= a.getTo_juvenile() && months < a.getTo_adult()) {
			return a.getAu_juvenile();
		}
		if (months < a.getTo_juvenile()) {
			return a.getAu_infant();
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (ear_ring != null && !ear_ring.isEmpty()) {
			sb.append(ear_ring).append(", ");
		}
		if (name != null && !name.isEmpty()) {
			sb.append(name).append(", ");
		}
		sb.append(getSex()).append(", ").append(color).append(", ").append(id_race_1).append(", ").append(date_birth);
		return sb.toString();
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] { id_livestock, ear_ring, getSex(), name, status, color, description, id_race_1.toString(),
				(id_race_2 == null) ? "" : id_race_2.toString(), (date_birth == null) ? "" : date_birth.toString(),
				(date_death == null) ? "" : date_death.toString(), (id_mother == null) ? "" : id_mother.getEar_ring(),
				(id_father == null) ? "" : id_father.getEar_ring() };
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Ear Tag", "Gender", "Name", "Status", "Color", "Description", "Breed 1", "Breed 2",
				"Date Birth", "Date Death", "Mother", "Father" };
	}
	/*
	 * id_livestock INT NOT NULL auto_increment, id_race_1 INT NOT NULL,
	 * id_race_2 INT, id_ranch INT NOT NULL, id_mother INT, id_father INT,
	 * ear_ring VARCHAR(100), gender INT, name VARCHAR(100), status
	 * VARCHAR(100), date_birth Date, date_death Date, img Longblob, img_dead
	 * Longblob, color VARCHAR(100) , description VARCHAR(300),
	 */
}
