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
@Table(name = "Livestock_Grupo")
public class LivestockGrupo implements Serializable, Writable {

	/*
	 * id_lg INT NOT NULL auto_increment, id_group INT NOT NULL, id_livestock
	 * INT NOT NULL, date_added DATE, date_removed DATE, active INT(1),
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_lg")
	private int id_lg;

	@JoinColumn(name = "id_group")
	@ManyToOne(cascade = CascadeType.ALL)
	private Grupo group;

	@JoinColumn(name = "id_livestock")
	@ManyToOne(cascade = CascadeType.ALL)
	private Livestock livestock;

	@Column(name = "date_added")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_added;

	@Column(name = "date_removed")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_removed;

	@Type(type = "true_false")
	@Column(name = "active")
	private boolean active;

	public LivestockGrupo() {
	}

	public LivestockGrupo(Grupo group, Livestock livestock, Date date_added, Date date_removed, boolean active) {
		super();
		this.group = group;
		this.livestock = livestock;
		this.date_added = date_added;
		this.date_removed = date_removed;
		this.active = active;
	}

	public int getId_lg() {
		return id_lg;
	}

	public void setId_lg(int id_lg) {
		this.id_lg = id_lg;
	}

	public Grupo getGroup() {
		return group;
	}

	public void setGroup(Grupo group) {
		this.group = group;
	}

	public Livestock getLivestock() {
		return livestock;
	}

	public void setLivestock(Livestock livestock) {
		this.livestock = livestock;
	}

	public Date getDate_added() {
		return date_added;
	}

	public void setDate_added(Date date_added) {
		this.date_added = date_added;
	}

	public Date getDate_removed() {
		return date_removed;
	}

	public void setDate_removed(Date date_removed) {
		this.date_removed = date_removed;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "LivestockGrupo [group=" + group + ", livestock=" + livestock + ", active=" + active + "]";
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
