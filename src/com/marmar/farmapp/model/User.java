package com.marmar.farmapp.model;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.writers.Writable;

import javafx.scene.image.Image;

@Entity
@Table(name = "User")
public class User implements Serializable, Writable {

	/*
	 * id_user INT NOT NULL auto_increment, id_ranch INT NOT NULL, username
	 * VARCHAR(64) NOT NULL, password VARCHAR(100) NOT NULL, usertype INT(1) NOT
	 * NULL, name VARCHAR(100) NOT NULL, img Longblob, PRIMARY KEY(id_user),
	 * FOREIGN KEY(id_ranch) REFERENCES Ranch(id_ranch)
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_user")
	private int id_user;

	@JoinColumn(name = "id_ranch")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ranch ranch;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "usertype")
	private int usertype;

	@Column(name = "name")
	private String name;

	@Column(name = "img")
	private byte[] img;

	public User() {
	}

	public User(Ranch ranch, String username, String password, int usertype, String name) {
		super();
		this.ranch = ranch;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.name = name;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public Ranch getRanch() {
		return ranch;
	}

	public void setRanch(Ranch ranch) {
		this.ranch = ranch;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		result = prime * result + id_user;
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
		User other = (User) obj;
		if (id_user != other.id_user)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id_user=" + id_user + ", username=" + username + ", usertype=" + usertype + ", name=" + name
				+ "]";
	}

	@Override
	public Object[] getAsArray() {
		return new Object[] { id_user, name, username, password, usertype };
	}

	@Override
	public String[] getNames() {
		return new String[] { "ID", "Full Name", "Username", "Password", "Usertype" };
	}
	public Image getImage() {
		if (img == null) {
			return null;
		}
		try {
			Image im = ImageManager.byteArraytoFXImage(img);
			return im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
