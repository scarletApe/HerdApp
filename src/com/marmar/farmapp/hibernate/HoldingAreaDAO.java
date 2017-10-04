package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.HoldingArea;

@Repository
public class HoldingAreaDAO {

	HibernateConector csf;

	public HoldingAreaDAO(HibernateConector csf) {
		this.csf = csf;
	}

	public Object saveNew(HoldingArea o) {
		return csf.saveObject(o);
	}

	public void update(HoldingArea r) {
		csf.updateObject(r);
	}

	public void delete(HoldingArea r) {
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HoldingArea> getAll() {
		String hql = "From HoldingArea";
		return (ArrayList<HoldingArea>) csf.executeHQLQuery(hql);
	}
}
