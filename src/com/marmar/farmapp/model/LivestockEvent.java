package com.marmar.farmapp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Livestock_Event")
public class LivestockEvent implements Serializable {

	/**
	 * id_event INT NOT NULL, id_livestock INT NOT NULL, comment VARCHAR(200),
	 * price REAL,
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_le")
	private int id_le;

	@JoinColumn(name = "id_event")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Event event;

	@JoinColumn(name = "id_livestock")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Livestock livestock;

	@Column(name = "comment")
	private String comment;

	@Column(name = "price")
	private Double price;

	public LivestockEvent() {

	}

	public LivestockEvent(Event event, Livestock livestock, String comment, Double price) {
		super();
		this.event = event;
		this.livestock = livestock;
		this.comment = comment;
		this.price = price;
	}

	public int getId_le() {
		return id_le;
	}

	public void setId_le(int id_le) {
		this.id_le = id_le;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Livestock getLivestock() {
		return livestock;
	}

	public void setLivestock(Livestock livestock) {
		this.livestock = livestock;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDate() {
		return event.getDate_event().toString();
	}

	@Override
	public String toString() {
		return "LivestockEvent [event=" + event + ", livestock=" + livestock + ", comment=" + comment + ", price="
				+ price + "]";
	}

}
