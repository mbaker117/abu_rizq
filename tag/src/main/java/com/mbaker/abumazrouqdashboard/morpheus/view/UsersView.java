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
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.enums.UserType;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.UserService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@ViewScoped
public class UsersView implements Serializable {
	private final static String ERROR_MSG="user.msg.failed";
	
	private List<User> users;

	private User selectedUser;

	private List<User> selectedUsers;

	@Inject
	private UserService userService;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	@Inject
	private ResourceBundle bundle;

	public List<User> getUsers() {
		users = userService.getAllEmployee();
		return users;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public void openNew() {

		User user = new User();
		user.setUserType(UserType.EMPLOYEE);

		this.selectedUser = user;
	}

	public void saveUser() {
		try {
			if (this.selectedUser.getId() == 0) {

				userService.add(selectedUser);
				FacesUtils.sucessMessage(bundle.getString("user.msg.add.success"));
			} else {
				userService.update(selectedUser);
				FacesUtils.sucessMessage(bundle.getString("user.msg.update.success"));
			}
			PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
		} catch (AbuMazrouqDashboardException e) {
			if (AbuMazrouqDashboardExceptionType.USER_ALREADY_EXIST.equals(e.getType())) {
				FacesUtils.errorMessage(bundle.getString("user.msg.username.invalid"));
			}else {
				FacesUtils.errorMessage(bundle.getString(ERROR_MSG));
			}
		}
		
		PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");

	}

	public void deleteUser()  {

		try {
			userService.delete(selectedUser.getId());
		} catch (AbuMazrouqDashboardException e) {
			FacesUtils.errorMessage(bundle.getString(ERROR_MSG));
		}
		this.selectedUser = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("user.msg.delete.success")));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedUsers()) {
			int size = this.selectedUsers.size();
			return size > 1 ? size + " Users selected" : "1 User selected";
		}

		return "Delete";
	}

	public boolean hasSelectedUsers() {
		return this.selectedUsers != null && !this.selectedUsers.isEmpty();
	}

	public void onRowToggle(ToggleEvent event) {
		if (event.getVisibility() == Visibility.VISIBLE) {

		}
	}
}
