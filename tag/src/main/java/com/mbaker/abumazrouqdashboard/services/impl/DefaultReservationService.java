package com.mbaker.abumazrouqdashboard.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.daos.ReservationDAO;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.ReservationService;

@Service
public class DefaultReservationService implements ReservationService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultReservationService.class);

	@Autowired
	private ReservationDAO reservationDAO;

	@Override
	public List<Reservation> findAll() {

		return reservationDAO.findAll();
	}

	@Override
	public Optional<Reservation> findById(long id) {

		return reservationDAO.findById(id);
	}

	@Override
	public void save(Reservation reservation) {
		if (Objects.isNull(reservation)) {
			final IllegalArgumentException ex = new IllegalArgumentException("reservation is null");
			LOG.error("[ReservationService]: {}", ex.getMessage());
			throw ex;
		}
		reservationDAO.save(reservation);
		LOG.error("[ReservationService]: reservation {} was added", reservation);
	}

	@Override
	public void delete(long id) throws AbuMazrouqDashboardException {
		Optional<Reservation> reservation = findById(id);
		if (reservation.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.EMPLOYEE_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.EMPLOYEE_NOT_FOUND.getMsg());
			LOG.error("[ReservationService]: {}", ex.getMessage());
			throw ex;
		}
		reservationDAO.delete(reservation.get());
		LOG.error("[ReservationService]: reservation {} was deleted", reservation.get());


	}

	@Override
	public Optional<Reservation> findByEmployeeName(String employeeName) {
		if (Strings.isBlank(employeeName)) {
			final IllegalArgumentException ex = new IllegalArgumentException("employeeName is null or empty");
			LOG.error("[ReservationService]: {}",ex.getMessage());
			throw ex;
		}
		
		return reservationDAO.findByEmployeeName(employeeName);
	}

	@Override
	public List<Reservation> findByDates(Date startDate, Date endDate) {
		if(Objects.isNull(startDate)) {
			final IllegalArgumentException ex = new IllegalArgumentException("startDate is null");
			LOG.error("[ReservationService]: {}",ex.getMessage());
			throw ex;
		}
		if(Objects.isNull(endDate)) {
			final IllegalArgumentException ex = new IllegalArgumentException("endDate is null");
			LOG.error("[ReservationService]: {}",ex.getMessage());
			throw ex;
		}

		return reservationDAO.findByDates(startDate, endDate);
	}

}
