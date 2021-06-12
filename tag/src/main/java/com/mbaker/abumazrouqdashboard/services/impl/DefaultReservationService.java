package com.mbaker.abumazrouqdashboard.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.daos.ReservationDAO;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Service
public class DefaultReservationService implements ReservationService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultReservationService.class);

	private final static String SERVICE_NAME = "ReservationService";
	private final static String LOG_MSG = "[ReservationService]: {}";
	private final static String RESERVATION_KEY = "reservation";
	private final static String CUTOMER_NAME_KEY = "cutomer name";
	private final static String EMP_NAME_KEY = "employee name";
	private final static String CUSTOMER_PHONE_NUMBER_KEY = "customerPhoneNumber";
	private final static String DATE_KEY = "date";
	private final static String START_DATE_KEY = "start date";
	private final static String END_DATE_KEY = "date";
	private final static String ITEMS_KEY = "items";
	private final static String STATUS_KEY = "status";

	@Autowired
	private ReservationDAO reservationDAO;

	@Autowired
	private CommonValidator commonValidator;

	@Override
	public List<Reservation> getAll() {

		return reservationDAO.findAll();
	}

	@Override
	public Optional<Reservation> getById(long id) {

		return reservationDAO.findById(id);
	}

	@Override
	public void add(Reservation reservation) {
		validateReservation(reservation);
		reservation.setStatus(ReservationStatus.PENDING);
		reservationDAO.save(reservation);
		LOG.error(LOG_MSG + " was added", reservation);
	}

	@Override
	public void update(Reservation reservation) {
		validateReservation(reservation);
		commonValidator.validateEmptyObject(reservation.getStatus(), STATUS_KEY, SERVICE_NAME);
		reservationDAO.save(reservation);
		LOG.error(LOG_MSG + " was updated", reservation);
	}

	@Override
	public void delete(long id) throws AbuMazrouqDashboardException {
		Optional<Reservation> reservation = getById(id);
		if (reservation.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.EMPLOYEE_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.EMPLOYEE_NOT_FOUND.getMsg());
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
		reservationDAO.delete(reservation.get());
		LOG.error(LOG_MSG + "  was deleted", reservation.get());

	}

	@Override
	public Optional<Reservation> getByEmployeeName(String employeeName) {
		commonValidator.validateEmptyString(employeeName, EMP_NAME_KEY, SERVICE_NAME);

		return reservationDAO.findByEmployeeName(employeeName);
	}

	@Override
	public List<Reservation> getByDates(Date startDate, Date endDate) {
		commonValidator.validateEmptyObject(endDate, END_DATE_KEY, SERVICE_NAME);
		commonValidator.validateEmptyObject(startDate, START_DATE_KEY, SERVICE_NAME);
		return reservationDAO.findByDates(startDate, endDate);
	}

	public void validateReservation(Reservation reservation) {
		commonValidator.validateEmptyObject(reservation, RESERVATION_KEY, SERVICE_NAME);
		commonValidator.validateEmptyObject(reservation.getDate(), DATE_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(reservation.getCustomerName(), CUTOMER_NAME_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(reservation.getCustomerPhoneNumber(), CUSTOMER_PHONE_NUMBER_KEY,
				SERVICE_NAME);
		commonValidator.validateEmptyString(reservation.getEmployeeName(), EMP_NAME_KEY, SERVICE_NAME);
		if (CollectionUtils.isEmpty(reservation.getItems())) {
			var ex = new IllegalArgumentException(ITEMS_KEY + " is null or empty");
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}

	}

}
