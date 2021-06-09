package com.mbaker.abumazrouqdashboard.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface ReservationService {

	public List<Reservation> findAll();
	
	public Optional<Reservation> findById(long id);
	
	public void save(Reservation reservation);
	
	public void delete(long id) throws AbuMazrouqDashboardException;
	
	public Optional<Reservation> findByEmployeeName(String employeeName);

	public List<Reservation> findByDates(Date startDate,Date endDate);

}
