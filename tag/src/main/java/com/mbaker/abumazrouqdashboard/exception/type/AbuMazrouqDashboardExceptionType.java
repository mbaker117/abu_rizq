
package com.mbaker.abumazrouqdashboard.exception.type;


public enum AbuMazrouqDashboardExceptionType {

	CATEGORY_NOT_FOUND("category not found"),ITEM_NOT_FOUND("item not found"),EMPLOYEE_NOT_FOUND("employee not found"),ADMIN_NOT_FOUND("admin not found"),USER_ALREADY_EXIST("user already exist");
	private String msg;


	private AbuMazrouqDashboardExceptionType(String msg) {
		this.msg = msg;
	}

	
	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

}
