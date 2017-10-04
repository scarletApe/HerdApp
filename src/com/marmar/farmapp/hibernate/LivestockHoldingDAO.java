package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.HoldingArea;
import com.marmar.farmapp.model.LivestockHolding;

@Repository
public class LivestockHoldingDAO {

	HibernateConector csf;

	public LivestockHoldingDAO(HibernateConector csf) {
		this.csf = csf;
	}

	public Object saveNew(LivestockHolding o) {
		return csf.saveObject(o);
	}

	public void update(LivestockHolding r) {
		csf.updateObject(r);
	}

	public void delete(LivestockHolding r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LivestockHolding> getAll() {
		String hql = "From LivestockHolding";
		return (ArrayList<LivestockHolding>) csf.executeHQLQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LivestockHolding> getLivestockInHold(HoldingArea event) {
		ArrayList<LivestockHolding> result = null;

		String hql = "From LivestockHolding where id_hold like '" + event.getId_hold() + "' AND active='T'";
		Session session = csf.getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			result = (ArrayList<LivestockHolding>) query.list();

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
