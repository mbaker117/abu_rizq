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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.primefaces.PrimeFaces;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import com.mbaker.abumazrouqdashboard.beans.MessageBundle;
import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;
import com.mbaker.abumazrouqdashboard.convertors.ReverseItemDataConvertor;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.facade.ReservationFacade;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@RequestScoped
@SessionScope
public class EditReservationsView implements Serializable {
	private final static String ERROR_MSG = "login.user.invalid.msg";

	@Inject
	private ReservationFacade reservationFacade;

	@Inject
	private ReservationService reservationService;

	@Inject
	private ReverseItemDataConvertor reverseItemDataConvertor;

	private List<ReservedItemData> itemsData;

	private Reservation reservation;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	/*
	 * @Inject private ResourceBundle bundle;
	 */
	
	public void init() throws AbuMazrouqDashboardException {
		
		itemsData = null;
		
	}

	public List<ReservedItemData> getItemsData() {
		itemsData = reservationFacade.getReservedItemByReservation(reservation);
		return itemsData;
	}

	public void setItemsData(List<ReservedItemData> itemsData) {
		this.itemsData = itemsData;
	}

	public Reservation getReservation() {
		return reservation;
	}
	

	public void setReservation(Reservation reservation) {

		this.reservation = reservation;
	}

	public void save() {

		List<ReservedItemData> collect = new ArrayList<ReservedItemData>();
		for (ReservedItemData data : itemsData) {
			if (data.getReservedAmount() > 0) {
				collect.add(data);
			}
		}

		if (CollectionUtils.isEmpty(collect)) {
			FacesUtils.errorMessage(MessageBundle.getBundle().getString("editReservation.error.invalidItem.label"));
			PrimeFaces.current().ajax().update("form:dt-items", "form:messages");
			return ;
		}
		if (Strings.isBlank(reservation.getCustomerName()) || Strings.isBlank(reservation.getCustomerPhoneNumber())
				|| Objects.isNull(reservation.getDate())) {
			FacesUtils.errorMessage(MessageBundle.getBundle().getString("editReservation.error.invalidData.label"));
			PrimeFaces.current().ajax().update("form:dt-items", "form:messages");
			return ;
		}
		reservation.setItems(reverseItemDataConvertor.convertAll(collect));
		reservationService.update(reservation);
		
		PrimeFaces.current().ajax().update("form:dt-items", "form:messages");
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		FacesUtils.sucessMessage(MessageBundle.getBundle().getString("editReservation.update.success.label"));
		FacesUtils.redirect("userpages/basicReports");
		
	}
	

}
