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
@Table(name = "Event")
public class Event implements Serializable, Writable {
	/*
	 * id_event INT NOT NULL auto_increment, id_eventtype INT, id_ranch INT,
	 * date_event Date, comments VARCHAR(200), total_amount REAL, stakeholder
	 * VARCHAR(200), stakeholder_contact VARCHAR(200), img Longblob,
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_event")
	private int id_event;

	// @Column(name = "eventType")
	@JoinColumn(name = "id_eventtype")
	@ManyToOne(cascade = CascadeType.ALL)
	private EventType eventType;

	@JoinColumn(name = "id_ranch")
	@ManyToOne(cascade = CascadeType.ALL)
	private Ranch ranch;

	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "date_event")
	private Date date_event;

	@Column(name = "comments")
	private String comments;

	@Column(name = "total_amount")
	private Double total_amount;

	@Column(name = "stakeholder")
	private String stakeholder;

	@Column(name = "stakeholder_contact")
	private String stakeholder_contact;

	@Column(name = "img")
	private byte[] img;

	public Event() {

	}

	public Event(EventType eventType, Ranch ranch, Date date_event, String comments, String stakeholder,
			String stakeholder_contact) {
		super();
		this.eventType = eventType;
		this.ranch = ranch;
		this.date_event = date_event;
		this.comments = comments;
		this.stakeholder = stakeholder;
		this.stakeholder_contact = stakeholder_contact;
	}

	public int getId_event() {
		return id_event;
	}

	public void setId_event(int id_event) {
		this.id_event = id_event;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Ranch getRanch() {
		return ranch;
	}

	public void setRanch(Ranch ranch) {
		this.ranch = ranch;
	}

	public Date getDate_event() {
		return date_event;
	}

	public void setDate_event(Date date_event) {
		this.date_event = date_event;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public String getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(String stakeholder) {
		this.stakeholder = stakeholder;
	}

	public String getStakeholder_contact() {
		return stakeholder_contact;
	}

	public void setStakeholder_contact(String stakeholder_contact) {
		this.stakeholder_contact = stakeholder_contact;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return eventType + " con "+stakeholder;
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] { 
				id_event, 
				(ranch==null)?"":ranch.getName(),
				(eventType==null)?"":eventType.getName(), 
				(date_event==null)?"":date_event.toString(),
				comments, 
				stakeholder,
				stakeholder_contact, 
				total_amount };
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Farm", "EventType", "Date", "Notes", "Stakeholder", "Contact",
				"Total" };
	}

}
