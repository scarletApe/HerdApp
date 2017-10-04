package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.Ranch;

@Repository
public class RanchDAO {

	@Autowired
	HibernateConector csf;

	public RanchDAO(HibernateConector csf) {
		this.csf = csf;
	}

	public Object saveNew(Ranch o) {
		return csf.saveObject(o);
	}

	public void update(Ranch r) {
		csf.updateObject(r);
	}

	public void delete(Ranch r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Ranch> getAll() {
		String hql = "From Ranch";
		return (ArrayList<Ranch>) csf.executeHQLQuery(hql);
	}
}
