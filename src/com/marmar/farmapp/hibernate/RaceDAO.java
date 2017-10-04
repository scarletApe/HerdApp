package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Race;

@Repository
public class RaceDAO {

	@Autowired
	HibernateConector csf;
	
	public RaceDAO(HibernateConector csf) {
		this.csf = csf;
	}
	

	public Object saveNew(Race o) {
		return csf.saveObject(o);
	}

	public void update(Race r) {
		csf.updateObject(r);
	}

	public void delete(Race r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Race> getAll() {
		String hql = "From Race";
		return (ArrayList<Race>) csf.executeHQLQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Race> getRaces(Animal a) {
		ArrayList<Race> result = null;

		String hql = "From Race where id_animal like '" + a.getId_animal() + "'";
		Session session = csf.getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			result = (ArrayList<Race>) query.list();

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
