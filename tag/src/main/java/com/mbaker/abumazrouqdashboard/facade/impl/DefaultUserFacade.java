package com.mbaker.abumazrouqdashboard.facade.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mbaker.abumazrouqdashboard.beans.data.UserData;
import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.convertors.UserConvertor;
import com.mbaker.abumazrouqdashboard.facade.UserFacade;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.services.UserService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

public class DefaultUserFacade implements UserFacade {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultReservationFacade.class);

	private final static String SERVICE_NAME = "UserFacade";
	private final static String LOG_MSG = "[UserFacade]: {}";
	private final static String RESERVATION_KEY = "reservation";

	@Autowired
	private CommonValidator commonValidator;

	@Autowired
	private UserService userService;

	@Autowired
	private UserConvertor userConvertor;

	@Override
	public List<UserData> getUsers() {
		List<User> users = userService.getAll();

		return userConvertor.convertAll(users);
	}

}
