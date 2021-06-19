package com.mbaker.abumazrouqdashboard.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface ReservationService {

	public List<Reservation> getAll();

	public Optional<Reservation> getById(long id);

	public void add(Reservation reservation);

	public void update(Reservation reservation);

	public void delete(long id) throws AbuMazrouqDashboardException;

	public List<Reservation> getByEmployeeName(String employeeName);

	public List<Reservation> getByDates(Date startDate, Date endDate);

	public List<Reservation> getByDatesAndStatus(Date startDate, Date endDate, ReservationStatus status);
	
	public List<Reservation> getByEmployeeId(long employeeId);
	
	public List<Reservation> getByDatesAndEmployeeName(Date startDate, Date endDate, String employeeName);



}
