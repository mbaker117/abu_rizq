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

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import com.mbaker.abumazrouqdashboard.beans.MessageBundle;
import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItem;
import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@RequestScoped
public class BasicReportsView implements Serializable {
	private final static String ERROR_MSG = "login.user.invalid.msg";

	@Inject
	private ReservationService reservationService;

	private List<Reservation> reservations;

	private List<ReservedItem> items;

	private Reservation selectedReservation;

	private Date startDate;

	private Date endDate;

	public void init() throws AbuMazrouqDashboardException {

		/*
		 * startDate = null; endDate = null; items = null; selectedReservation = null;
		 * 
		 * reservations = null;
		 */
	}

	/**
	 * the bundle variable of type ResourceBundle
	 */
	/*
	 * @Inject private ResourceBundle bundle;
	 */

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;

	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;

	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation getSelectedReservation() {
		return selectedReservation;
	}

	public void setSelectedReservation(Reservation selectedReservation) {
		this.selectedReservation = selectedReservation;
	}

	public List<ReservedItem> getItems() {
		// items = selectedReservation.getItems();
		return items;
	}

	public void setItems(List<ReservedItem> items) {
		this.items = items;
	}

	public void search() throws IOException {

		reservations = reservationService.getByDates(startDate, endDate);
		PrimeFaces.current().ajax().update("form:dt-reservations", "form:messages");
		
	}

	public void rowToggler(ToggleEvent event) {
		if (event.getVisibility() == Visibility.VISIBLE) {
			selectedReservation = (Reservation) event.getData();

		}
	}

	public void deleteReservation() throws IOException {
		if (Objects.isNull(selectedReservation)) {
			return;
		}
		try {

			reservationService.delete(selectedReservation.getId());
			search();
			FacesUtils.sucessMessage(MessageBundle.getBundle().getString("basicReports.msg.delete.success.label"));
		} catch (AbuMazrouqDashboardException e) {
			FacesUtils.sucessMessage(MessageBundle.getBundle().getString("basicReports.msg.erorr.label"));
		}
		PrimeFaces.current().ajax().update("form:dt-reservations", "form:messages");

	}

	public void editReservation() {
		FacesUtils.redirect("userpages/editReservations");
	}

	public void completeReservation() {
		if (Objects.isNull(selectedReservation)) {
			return;
		}
		selectedReservation.setStatus(ReservationStatus.COMPLETED);
		reservationService.update(selectedReservation);
		PrimeFaces.current().ajax().update("form:dt-reservations", "form:messages");

	}

	public boolean canEdit(Reservation reservation, int x) {
		if (reservation == null) {
			return false;
		}
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		User user = (User) sessionMap.get("user");
		return user.getId() == reservation.getEmployeeId();
	}
}
