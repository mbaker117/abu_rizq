package com.mbaker.abumazrouqdashboard.facade;

import java.util.Date;
import java.util.List;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;

public interface ReservationFacade {
	
	public List<ReservedItemData> getReservedItemByDates(Date startDate, Date endDate);
	
	public List<ReservedItemData> getReservedItemByReservation(Reservation reservation);

}
