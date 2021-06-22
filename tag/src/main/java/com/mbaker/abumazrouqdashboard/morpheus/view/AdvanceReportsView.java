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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.util.Strings;
import org.primefaces.PrimeFaces;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItem;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@RequestScoped
@SessionScope
public class AdvanceReportsView implements Serializable {
	private final static String ERROR_MSG = "login.user.invalid.msg";

	@Inject
	private ReservationService reservationService;

	private List<Reservation> reservations;

	private List<ReservedItem> items;

	private Reservation selectedReservation;

	private Date startDate;

	private Date endDate;

	private String empName;

	public void init() throws AbuMazrouqDashboardException {
		/*
		 * startDate = null; endDate = null; items = null; selectedReservation = null;
		 * reservations=null;
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
		return items;
	}

	public void setItems(List<ReservedItem> items) {
		this.items = items;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void search() {

		reservations = reservationService.getByDatesAndEmployeeName(startDate, endDate, empName);
		PrimeFaces.current().ajax().update("form:dt-reservations", "form:messages");
	}

	public void print() {
		FacesUtils.redirect("userpages/invoice");
	}
}
