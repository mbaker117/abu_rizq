package com.mbaker.abumazrouqdashboard.convertors;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.data.UserData;
import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.services.ReservationService;

@Component
public class UserConvertor extends Convertor<User, UserData> {

	@Autowired
	private ReservationService reservationService;

	@Override
	public UserData convert(User user) {

		if (Objects.isNull(user)) {
			return null;
		}

		
		UserData userData = new UserData();
		userData.setId(user.getId());
		userData.setUserType(user.getUserType());
		userData.setUsername(user.getUsername());
		userData.setId(user.getId());
		userData.setPassword(user.getPassword());
		userData.setCanWrite(user.isCanWrite());
		userData.setName(user.getName());
		List<Reservation> reservations = reservationService.getByEmployeeId(user.getId());
		if (!CollectionUtils.isEmpty(reservations)) {
			userData.setNumberOfReservations(Long.valueOf(reservations.size()));
		}
		
		return userData;
	}

}
