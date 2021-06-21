package com.mbaker.abumazrouqdashboard.beans;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class MessageBundle {

	public static ResourceBundle getBundle() {
		return ResourceBundle.getBundle("com.mbaker.abumazrouqdashboard.morpheus.resource.messages.Messages");
	}

}
