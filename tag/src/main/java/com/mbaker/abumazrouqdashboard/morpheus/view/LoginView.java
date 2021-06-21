/*
   Copyright 2009-2021 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.mbaker.abumazrouqdashboard.morpheus.view;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.mbaker.abumazrouqdashboard.beans.MessageBundle;
import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.services.UserService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@ViewScoped
public class LoginView implements Serializable {
	private final static String ERROR_MSG = "login.user.invalid.msg";

	@Inject
	private UserService userService;

	private String username;

	private String password;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	/*
	 * @Inject private ResourceBundle bundle;
	 */

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

	public void validateUser() {
		Optional<User> user = userService.login(username, password);

		if (user.isPresent()) {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("user", user.get());
			FacesUtils.redirect("userpages/main");
			return;

		} else {

			FacesUtils.errorMessage(MessageBundle.getBundle().getString(ERROR_MSG));
		}

		PrimeFaces.current().ajax().update("form:messages");

	}
	
	public void logout() {
		 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		 FacesUtils.redirect("login");
		 
		 
	}

}
