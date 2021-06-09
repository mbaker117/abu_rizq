package com.mbaker.abumazrouqdashboard.enums;

public enum UserType {

	
	ADMIN("admin"), EMPLOYEE("employee");

	private final String code;

	private UserType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
