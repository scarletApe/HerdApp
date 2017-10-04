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

import com.marmar.farmapp.util.writers.Writable;

@Entity
@Table(name = "Grupo")
public class Grupo implements Serializable, Writable {

	/*
	 * id_group INT NOT NULL auto_increment, id_ranch INT, name VARCHAR,
	 * description VARCHAR, img Longblob, date_created DATE,
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_group")
	private int id_group;

	@JoinColumn(name = "id_ranch")
	@ManyToOne(cascade = CascadeType.ALL)
	private Ranch ranch;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "img")
	private byte[] img;

	@Column(name = "date_created")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date_created;

	public Grupo() {
	}

	public Grupo(int id_group, Ranch ranch, String name, String description, byte[] img, Date date_created) {
		super();
		this.id_group = id_group;
		this.ranch = ranch;
		this.name = name;
		this.description = description;
		this.img = img;
		this.date_created = date_created;
	}

	public int getId_group() {
		return id_group;
	}

	public void setId_group(int id_group) {
		this.id_group = id_group;
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

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	@Override
	public String toString() {
		return "Grupo [name=" + name + "]";
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
