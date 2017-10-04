package com.marmar.farmapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ranch")
public class Ranch implements Serializable{

	/*
	 * id_ranch INT NOT NULL auto_increment, name VARCHAR(100) NOT NULL, rfc
	 * VARCHAR(100) NOT NULL, iron Longblob, address VARCHAR(200), phone
	 * VARCHAR(30), email VARCHAR(100), logo Longblob, img Longblob, area INT,
	 * PRIMARY KEY(id_ranch)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_ranch")
	private int id_ranch;

	@Column(name = "name")
	private String name;

	@Column(name = "rfc")
	private String rfc;

	@Column(name = "iron")
	private byte[] iron;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "logo")
	private byte[] logo;

	@Column(name = "img")
	private byte[] img;


	public Ranch() {
	}

	public Ranch(String name, String rfc, String address, String phone, String email) {
		this.name = name;
		this.rfc = rfc;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public int getId_ranch() {
		return id_ranch;
	}

	public void setId_ranch(int id_ranch) {
		this.id_ranch = id_ranch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public byte[] getIron() {
		return iron;
	}

	public void setIron(byte[] iron) {
		this.iron = iron;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_ranch;
		result = prime * result + ((rfc == null) ? 0 : rfc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ranch other = (Ranch) obj;
		if (id_ranch != other.id_ranch)
			return false;
		if (rfc == null) {
			if (other.rfc != null)
				return false;
		} else if (!rfc.equals(other.rfc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name.toUpperCase();
	}

}
