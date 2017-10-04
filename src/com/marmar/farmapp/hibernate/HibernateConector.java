/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marmar.farmapp.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author manuelmartinez
 */
@Repository
public class HibernateConector {

	@Autowired
	public SessionFactory factory;
	
	public HibernateConector(SessionFactory factory){
		this.factory = factory;
	}

	public SessionFactory getSf() {
		return factory;
	}

	public void setSf(SessionFactory sf) {
		this.factory = sf;
	}

	public Object saveObject(Object object) {
		Session session = getSf().openSession();
		Transaction tx = null;
		Object objectId = 0;
		try {
			tx = session.beginTransaction();
			objectId = session.save(object);
			tx.commit();
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.err.println(he.getMessage());
			he.printStackTrace();
		} finally {
			session.close();
		}
		return objectId;
	}

	public void updateObject(Object object) {
		Session session = getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(object);
			tx.commit();
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.err.println(he.getMessage());
			he.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteObject(Object object) {
		Session session = getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(object);
			tx.commit();
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.err.println(he.getMessage());
			he.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public List executeHQLQuery(String hql) {
		List result = null;
		Session session = getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			result = query.list();
			tx.commit();

		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.err.println(he.getMessage());
			he.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
}
