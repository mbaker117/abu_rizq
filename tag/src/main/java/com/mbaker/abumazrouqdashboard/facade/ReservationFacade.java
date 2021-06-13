package com.mbaker.abumazrouqdashboard.facade;

import java.util.Date;
import java.util.List;

import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;

public interface ReservationFacade {
	
	public List<ReservedItemData> getReservedItemByDates(Date startDate, Date endDate);

}
