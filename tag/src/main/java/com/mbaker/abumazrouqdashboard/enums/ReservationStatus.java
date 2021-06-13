package com.mbaker.abumazrouqdashboard.enums;

public enum ReservationStatus {

	COMPLETED("completed"),CANCELED("canceled"),PENDING("pending");
	
	
	private final String code;

	private ReservationStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
