package com.mbaker.abumazrouqdashboard.convertors;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.data.UserData;
import com.mbaker.abumazrouqdashboard.beans.model.User;

@Component
public class UserConvertor extends Convertor<User,UserData> {

	@Override
	public UserData convert(User user) {

		if (Objects.isNull(user)) {
			return null;
		}

		UserData userData = new UserData();
		userData.setUserType(user.getUserType());
		userData.setUsername(user.getUsername());
		userData.setId(user.getId());
		userData.setPassword(user.getPassword());
		userData.setCanWrite(user.isCanWrite());
		userData.setName(user.getName());

		return userData;
	}



}
