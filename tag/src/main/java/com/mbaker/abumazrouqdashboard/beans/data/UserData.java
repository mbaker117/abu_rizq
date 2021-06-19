package com.mbaker.abumazrouqdashboard.beans.data;

import com.mbaker.abumazrouqdashboard.enums.UserType;

public class UserData {

	private long id;

	private String username;

	private String password;

	private String name;

	private boolean canWrite;

	private UserType userType;

	private long numberOfReservations;

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

	public long getNumberOfReservations() {
		return numberOfReservations;
	}

	public void setNumberOfReservations(long numberOfReservations) {
		this.numberOfReservations = numberOfReservations;
	}


}
