package com.mbaker.abumazrouqdashboard.facade;

import java.util.Date;
import java.util.List;

import com.mbaker.abumazrouqdashboard.beans.model.ReservedItem;

public interface ReservationFacade {
	
	public List<ReservedItem> getReservedItemByDates(Date startDate, Date endDate);

}
