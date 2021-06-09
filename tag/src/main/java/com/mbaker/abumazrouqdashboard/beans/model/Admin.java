package com.mbaker.abumazrouqdashboard.beans.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "ADMIN")
public class Admin {
	
	@Id
	@SequenceGenerator(name="admin_generator", sequenceName = "admin_generator", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_generator")
	@NotNull
	@Column(name = "id")
	private long id;
	
	@NotNull
	@Column(name = "username")
	private String username;
	
	@NotNull
	@Column(name = "password")
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
}
