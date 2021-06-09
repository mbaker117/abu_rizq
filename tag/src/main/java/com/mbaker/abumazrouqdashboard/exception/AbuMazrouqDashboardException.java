package com.mbaker.abumazrouqdashboard.exception;

import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;

public class AbuMazrouqDashboardException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6129394610820931036L;
	private final AbuMazrouqDashboardExceptionType type;
	


	public AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType type,String msg) {
		super(msg);
		this.type = type;
	}

	public AbuMazrouqDashboardExceptionType getType() {
		return type;
	}



}
