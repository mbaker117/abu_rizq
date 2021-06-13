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
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.primefaces.PrimeFaces;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;
import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.convertors.ReverseItemDataConvertor;
import com.mbaker.abumazrouqdashboard.facade.ReservationFacade;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@ViewScoped
public class AddReservationsView implements Serializable {
	private final static String ERROR_MSG = "login.user.invalid.msg";

	@Inject
	private ReservationFacade reservationFacade;

	@Inject
	private ReservationService reservationService;

	@Inject
	private ReverseItemDataConvertor reverseItemDataConvertor;

	private List<ReservedItemData> itemsData;

	private List<ReservedItemData> selectedItems;

	private Date startDate;

	private Date endDate;

	private String customerName;

	private String customerNumber;

	private Date reservationDate;

	private String notes;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	@Inject
	private ResourceBundle bundle;

	public List<ReservedItemData> getItemsData() {
		return itemsData;
	}

	public void setItemsData(List<ReservedItemData> itemsData) {
		this.itemsData = itemsData;
	}

	public List<ReservedItemData> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<ReservedItemData> selectedItems) {
		this.selectedItems = selectedItems;
	}

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void save() {

		List<ReservedItemData> collect = selectedItems.stream().filter(e -> e.getReservedAmount() > 0)
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(collect)) {
			FacesUtils.errorMessage(bundle.getString("reservations.error.invalidItem.label"));
			PrimeFaces.current().ajax().update("form:dt-items", "form:messages");
			return;
		}
		if (Strings.isBlank(customerName) || Strings.isBlank(customerNumber) || Objects.isNull(reservationDate)) {
			FacesUtils.errorMessage(bundle.getString("reservations.error.invalidData.label"));
			PrimeFaces.current().ajax().update("form:dt-items", "form:messages");
			return;
		}
		Reservation reservation = new Reservation();
		reservation.setCustomerName(customerName);
		reservation.setCustomerPhoneNumber(customerNumber);
		reservation.setDate(reservationDate);
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		reservation.setEmployeeName(user.getName());
		reservation.setEmployeeId(user.getId());
		reservation.setItems(reverseItemDataConvertor.convertAll(collect));
		reservationService.add(reservation);
		PrimeFaces.current().ajax().update("form:dt-items", "form:messages");
		FacesUtils.sucessMessage(bundle.getString("reservations.add.success.label"));
		FacesUtils.redirect("userpages/main");
		reservation=null;

	}

	public void search() {

		itemsData = reservationFacade.getReservedItemByDates(startDate, endDate);
		PrimeFaces.current().ajax().update("form:dt-items", "form:messages");

		
		
	}

	public boolean hasSelectedItems() {

		return this.selectedItems != null && !this.selectedItems.isEmpty();
	}

	public boolean canAdd() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		User user = (User) sessionMap.get("user");
		return user.isCanWrite();
	}
}
