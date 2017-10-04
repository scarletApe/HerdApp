package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.EventType;

@Repository
public class EventTypeDAO {

	@Autowired
	HibernateConector csf;

	public EventTypeDAO(HibernateConector csf) {
		this.csf = csf;
	}
	
	public Object saveNew(EventType o) {
		return csf.saveObject(o);
	}

	public void update(EventType r) {
		csf.updateObject(r);
	}

	public void delete(EventType r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<EventType> getAll() {
		String hql = "From EventType";
		return (ArrayList<EventType>) csf.executeHQLQuery(hql);
	}
}
