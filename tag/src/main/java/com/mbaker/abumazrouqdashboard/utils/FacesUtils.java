package com.mbaker.abumazrouqdashboard.utils;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtils {

	public static void errorMessages(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	public static void errorMessage(String summary) {	
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	}

	public static void sucessMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}

	public static void warnMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	public static void redirect(String page) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getApplicationContextPath() + "/" + page+".xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void redirect(String page,String param) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getApplicationContextPath() + "/" + page+".xhtml?"+param);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
