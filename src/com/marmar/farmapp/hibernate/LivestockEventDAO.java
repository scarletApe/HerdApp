package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.Event;
import com.marmar.farmapp.model.LivestockEvent;

@Repository
public class LivestockEventDAO {

	@Autowired
	HibernateConector csf;
	
	public LivestockEventDAO(HibernateConector csf) {
		this.csf = csf;
	}

	public Object saveNew(LivestockEvent o) {
		return csf.saveObject(o);
	}

	public void update(LivestockEvent r) {
		csf.updateObject(r);
	}

	public void delete(LivestockEvent r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LivestockEvent> getAll() {
		String hql = "From Livestock_Event";
		return (ArrayList<LivestockEvent>) csf.executeHQLQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LivestockEvent> getLivestockAtEvent(Event event) {
		ArrayList<LivestockEvent> result = null;

		String hql = "From LivestockEvent where id_event like '" + event.getId_event() + "'";
		Session session = csf.getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			result = (ArrayList<LivestockEvent>) query.list();

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
