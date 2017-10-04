package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.User;

@Repository
public class UserDAO {

	@Autowired
    HibernateConector csf;
	
	public UserDAO(HibernateConector csf) {
		this.csf = csf;
	}
	
	public Object saveNew(User o) {
		return csf.saveObject(o);
	}

	public void update(User r) {
		csf.updateObject(r);
	}

	public void delete(User r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<User> getAll() {
		String hql = "From User";
		return (ArrayList<User>) csf.executeHQLQuery(hql);
	}
	@SuppressWarnings("unchecked")
	public User get(String username, String contrasena) {
		ArrayList<User> result = null;
		User u = null;
		/*
		 * SELECT * FROM DONORS WHERE RELIGION_CODE <> (SELECT RELIGION_CODE
		 * FROM RELIGIONS WHERE UPPER(RELIGION_NAME) LIKE 'PASTAFARISMO');
		 */
		String hql = "From User where username like '" + username + "' and password like '" + contrasena + "'";
		Session session = csf.getSf().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			result = (ArrayList<User>) query.list();

			if (result != null && !result.isEmpty()) {
				// for (Usuario b : result) {
				// Hibernate.initialize(b.getTipoUsario());
				// }
				u = result.get(0);
			}

			tx.commit();
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.err.println(he.getMessage());
		} finally {
			session.close();
		}
		return u;
	}
}
