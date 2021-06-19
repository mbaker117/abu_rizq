package com.mbaker.abumazrouqdashboard.convertors;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.data.UserData;
import com.mbaker.abumazrouqdashboard.beans.model.User;

@Component
public class ReverseUserConvertor extends Convertor<UserData, User> {

	@Override
	public User convert(UserData userData) {

		if (Objects.isNull(userData)) {
			return null;
		}

		User user = new User();
		user.setId(userData.getId());
		user.setUserType(userData.getUserType());
		user.setUsername(userData.getUsername());
		user.setId(userData.getId());
		user.setPassword(userData.getPassword());
		user.setCanWrite(userData.isCanWrite());
		user.setName(userData.getName());

		return user;
	}

}
