package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.Event;
import com.marmar.farmapp.model.EventType;

@Repository
public class EventDAO {

	@Autowired
	HibernateConector csf;

	public EventDAO(HibernateConector csf) {
		this.csf = csf;
	}
	
	public Object saveNew(Event o) {
		return csf.saveObject(o);
	}

	public void update(Event r) {
		csf.updateObject(r);
	}

	public void delete(Event r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Event> getAll() {
		String hql = "From Event";
		return (ArrayList<Event>) csf.executeHQLQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Event> getEventType(EventType et) {
		ArrayList<Event> result = null;

		String hql = "From Event where id_eventtype like '" + et.getId_eventtype() + "'";
		Session session = csf.getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			result = (ArrayList<Event>) query.list();

			tx.commit();
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.err.println(he.getMessage());
		} finally {
			session.close();
		}
		return result;
	}
}
