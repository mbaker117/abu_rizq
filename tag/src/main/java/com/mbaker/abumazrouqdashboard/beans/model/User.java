package com.mbaker.abumazrouqdashboard.beans.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mbaker.abumazrouqdashboard.enums.UserType;
import com.sun.istack.NotNull;


@Entity
@Table(name = "USER")
public class User {
	@Id
	@SequenceGenerator(name="user_generator", sequenceName = "user_generator", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@NotNull
	@Column(name = "id")
	private long id;
	
	@NotNull
	@Column(name = "username")
	private String username;
	
	@NotNull
	@Column(name = "password")
	private String password;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "canWrite")
	private boolean canWrite;
	
	@NotNull
	@Column(name = "userType")
	private UserType userType;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanWrite() {
		return canWrite;
	}

	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
