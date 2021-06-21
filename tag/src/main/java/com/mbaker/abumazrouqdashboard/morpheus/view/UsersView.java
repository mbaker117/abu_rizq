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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;

import com.mbaker.abumazrouqdashboard.beans.MessageBundle;
import com.mbaker.abumazrouqdashboard.beans.data.UserData;
import com.mbaker.abumazrouqdashboard.beans.lazymodel.LazyUserDataModel;
import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.convertors.ReverseUserConvertor;
import com.mbaker.abumazrouqdashboard.convertors.UserConvertor;
import com.mbaker.abumazrouqdashboard.enums.UserType;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.UserService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@ViewScoped
public class UsersView implements Serializable {
	private final static String ERROR_MSG = "user.msg.failed";

	private List<UserData> users;

	private UserData selectedUser;

	private List<UserData> selectedUsers;

	@Autowired
	private UserConvertor userConvertor;

	@Autowired
	private ReverseUserConvertor reverseUserConvertor;

	@Inject
	private UserService userService;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	/*
	 * @Inject private ResourceBundle bundle;
	 */

	public List<UserData> getUsers() {
		users = userConvertor.convertAll(userService.getAllEmployee());
		return users;
	}

	public void setUsers(List<UserData> users) {
		this.users = users;
	}

	public void setSelectedUser(UserData selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserData getSelectedUser() {
		return selectedUser;
	}

	public List<UserData> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<UserData> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}


	public void openNew() {

		UserData user = new UserData();
		user.setUserType(UserType.EMPLOYEE);

		this.selectedUser = user;
	}

	public void saveUser() {
		try {
			User user = reverseUserConvertor.convert(selectedUser);
			if (this.selectedUser.getId() == 0) {

				userService.add(user);
				FacesUtils.sucessMessage(MessageBundle.getBundle().getString("user.msg.add.success"));
			} else {
				userService.update(user);
				FacesUtils.sucessMessage(MessageBundle.getBundle().getString("user.msg.update.success"));
			}
			PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
		} catch (AbuMazrouqDashboardException e) {
			if (AbuMazrouqDashboardExceptionType.USER_ALREADY_EXIST.equals(e.getType())) {
				FacesUtils.errorMessage(MessageBundle.getBundle().getString("user.msg.username.invalid"));
			} else {
				FacesUtils.errorMessage(MessageBundle.getBundle().getString(ERROR_MSG));
			}
		}

		PrimeFaces.current().ajax().update("form:messages", "form:dt-Users");
		// selectedUser = null;

	}

	public void deleteUser() {

		try {
			userService.delete(selectedUser.getId());
		} catch (AbuMazrouqDashboardException e) {
			FacesUtils.errorMessage(MessageBundle.getBundle().getString(ERROR_MSG));
		}
		this.selectedUser = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(MessageBundle.getBundle().getString("user.msg.delete.success")));
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
