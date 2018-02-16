package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.HoldingArea;
import com.marmar.farmapp.model.Livestock;
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
	public ArrayList<LivestockHolding> getLivestockInHold(HoldingArea hold) {
		ArrayList<LivestockHolding> result = null;

		String hql = "From LivestockHolding where id_hold like '" + hold.getId_hold() + "' AND active='T'";
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
//		// removes the animals that are inactive, even though they might be active in
//		// said holding area
//		for (int i = 0; i < result.size(); i++) {
//			if (result.get(i).getLivestock().isActive()) {
//				//nothing
//			} else {
//				LivestockHolding l =result.remove(i);
//				System.out.println(l);
//			}
//		}
		return result;
	}

	/**
	 * Get the livestock that is not assigned to any holding area, it only gets the
	 * active animals.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Livestock> getUnassignedLivestock() {
		ArrayList<Livestock> result = null;

		Session session = csf.getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String sql = "SELECT * FROM Livestock WHERE id_livestock NOT IN (SELECT LH.id_livestock FROM Livestock_Holding LH WHERE LH.active='T') AND active='T'";
			result = (ArrayList<Livestock>) session.createSQLQuery(sql).addEntity(Livestock.class).list();

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

// @SuppressWarnings("unchecked")
// public ArrayList<Livestock> getLivestockThatAreActiveInAHold() {
// ArrayList<LivestockHolding> result = null;
//
// String hql = "From LivestockHolding where active='T'";
// Session session = csf.getSf().openSession();
// Transaction tx = null;
// try {
// tx = session.beginTransaction();
// Query query = session.createQuery(hql);
// result = (ArrayList<LivestockHolding>) query.list();
//
// tx.commit();
// } catch (HibernateException he) {
// if (tx != null) {
// tx.rollback();
// }
// System.err.println(he.getMessage());
// } finally {
// session.close();
// }
// ArrayList<Livestock> activeLive = new ArrayList<>(result.size());
// for (int i = 0; i < result.size(); i++) {
// activeLive.add(result.get(i).getLivestock());
// }
// return activeLive;
// }
