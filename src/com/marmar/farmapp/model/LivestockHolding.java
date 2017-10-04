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

import org.hibernate.annotations.Type;

import com.marmar.farmapp.util.writers.Writable;

@Entity
@Table(name = "Livestock_Holding")
public class LivestockHolding implements Serializable, Writable {

	/*
	 * id_lh INT NOT NULL auto_increment, id_hold INT NOT NULL, id_livestock INT
	 * NOT NULL, date_added DATE, date_removed DATE, active INT(1),
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_lh")
	private int id_lh;

	@JoinColumn(name = "id_hold")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private HoldingArea hold;

	@JoinColumn(name = "id_livestock")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Livestock livestock;

	@Column(name = "date_added")
	private Date date_added;

	@Column(name = "date_removed")
	private Date date_removed;

	@Type(type = "true_false")
	@Column(name = "active")
	private boolean active;

	public LivestockHolding() {

	}

	public LivestockHolding(HoldingArea hold, Livestock livestock, Date date_added, boolean active) {
		super();
		this.hold = hold;
		this.livestock = livestock;
		this.date_added = date_added;
		this.active = active;
	}

	public int getId_lh() {
		return id_lh;
	}

	public void setId_lh(int id_lh) {
		this.id_lh = id_lh;
	}

	public HoldingArea getHold() {
		return hold;
	}

	public void setHold(HoldingArea hold) {
		this.hold = hold;
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

	@SuppressWarnings("deprecation")
	public int getDuration() {
		if (date_added == null) {
			return 0;
		}
		// calcular tiempo de estancia
		Date actual = new Date();

		int actual_months = (actual.getMonth() + 1) + (actual.getYear() * 12);
		int d_months = (date_added.getMonth() + 1) + (date_added.getYear() * 12);

		return actual_months - d_months;
	}

	@Override
	public String toString() {
		return "LivestockHolding [hold=" + hold + ", livestock=" + livestock + ", active=" + active + "]";
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
