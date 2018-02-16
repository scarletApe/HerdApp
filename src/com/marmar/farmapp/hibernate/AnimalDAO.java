package com.marmar.farmapp.hibernate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marmar.farmapp.model.Animal;

@Repository
public class AnimalDAO {

	@Autowired
	HibernateConector csf;
	
	public AnimalDAO(HibernateConector csf) {
		this.csf = csf;
	}

	public Object saveNew(Animal o) {
		return csf.saveObject(o);
	}

	public void update(Animal r) {
		csf.updateObject(r);
	}

	public void delete(Animal r){
		csf.deleteObject(r);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Animal> getAll() {
		String hql = "From Animal";
		return (ArrayList<Animal>) csf.executeHQLQuery(hql);
	}
}
