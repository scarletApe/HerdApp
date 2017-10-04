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

import com.marmar.farmapp.util.writers.Writable;

@Entity
@Table(name = "Pregnancy")
public class Pregnancy implements Serializable, Writable {

	/*
	 * id_prego INT NOT NULL auto_increment, id_mother INT, id_father INT,
	 * id_calf INT, date_inpregnated DATE, date_due DATE, date_birth DATE,
	 * condition_birth VARCHAR, details VARCHAR, active INT(1),
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_prego")
	private int id_prego;

	@JoinColumn(name = "id_mother")
	@ManyToOne(cascade = CascadeType.ALL)
	private Livestock id_mother;

	@JoinColumn(name = "id_father")
	@ManyToOne(cascade = CascadeType.ALL)
	private Livestock id_father;

	@JoinColumn(name = "id_calf")
	@ManyToOne(cascade = CascadeType.ALL)
	private Livestock id_calf;

	@Column(name = "date_inpregnated")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_inpregnated;

	@Column(name = "date_due")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_due;

	@Column(name = "date_birth")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_birth;

	@Column(name = "condition_birth")
	private String condition_birth;

	@Column(name = "details")
	private String details;

	@Type(type = "true_false")
	@Column(name = "active")
	private boolean active;

	public Pregnancy() {
	}

	public Pregnancy(Livestock id_mother, Livestock id_father, Date date_inpregnated, Date date_due, boolean active) {
		super();
		this.id_mother = id_mother;
		this.id_father = id_father;
		this.date_inpregnated = date_inpregnated;
		this.date_due = date_due;
		this.active = active;
	}

	public int getId_prego() {
		return id_prego;
	}

	public void setId_prego(int id_prego) {
		this.id_prego = id_prego;
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

	public Livestock getId_calf() {
		return id_calf;
	}

	public void setId_calf(Livestock id_calf) {
		this.id_calf = id_calf;
	}

	public Date getDate_inpregnated() {
		return date_inpregnated;
	}

	public void setDate_inpregnated(Date date_inpregnated) {
		this.date_inpregnated = date_inpregnated;
	}

	public Date getDate_due() {
		return date_due;
	}

	public void setDate_due(Date date_due) {
		this.date_due = date_due;
	}

	public Date getDate_birth() {
		return date_birth;
	}

	public void setDate_birth(Date date_birth) {
		this.date_birth = date_birth;
	}

	public String getCondition_birth() {
		return condition_birth;
	}

	public void setCondition_birth(String condition_birth) {
		this.condition_birth = condition_birth;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Pregnancy [id_mother=" + id_mother + ", date_due=" + date_due + ", active=" + active + "]";
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] {};
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Ear Tag", "Gender", "Name", "Status", "Color", "Description", "Breed 1", "Breed 2",
				"Date Birth", "Date Death", "Farm", "Mother", "Father" };
	}
}
