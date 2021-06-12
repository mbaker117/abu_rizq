package com.mbaker.abumazrouqdashboard.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface ReservationService {

	public List<Reservation> getAll();
	
	public Optional<Reservation> getById(long id);
	
	public void add(Reservation reservation);
	
	public void update(Reservation reservation);
	
	public void delete(long id) throws AbuMazrouqDashboardException;
	
	public Optional<Reservation> getByEmployeeName(String employeeName);

	public List<Reservation> getByDates(Date startDate,Date endDate);

}
