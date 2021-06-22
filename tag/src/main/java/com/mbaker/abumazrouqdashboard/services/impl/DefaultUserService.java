package com.mbaker.abumazrouqdashboard.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.daos.UserDAO;
import com.mbaker.abumazrouqdashboard.enums.UserType;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.UserService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Service
public class DefaultUserService implements UserService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);
	private final static String SERVICE_NAME = "UserService";
	private final static String LOG_MSG = "[UserService]: {}";
	private final static String NAME_KEY = "name";
	private final static String USERNAME_KEY = "username";
	private final static String PASSWORD_KEY = "password";
	private final static String USER_TYPE_KEY = "userType";
	private final static String USER_KEY = "user";

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CommonValidator commonValidator;

	@Override
	public List<User> getAll() {

		return userDAO.findAll();
	}

	@Override
	public Optional<User> getById(long id) {

		return userDAO.findById(id);
	}

	@Override
	public void add(User user) throws AbuMazrouqDashboardException {
		validateUser(user);
		userDAO.save(user);
		LOG.error("[{}]: user {} was added", SERVICE_NAME, user);

	}

	@Override
	public void update(User user) throws AbuMazrouqDashboardException {
		validateUser(user);
		userDAO.save(user);
		LOG.error("[{}]: user {} was updated", SERVICE_NAME, user);

	}

	@Override
	public void delete(long id) throws AbuMazrouqDashboardException {
		Optional<User> user = getById(id);
		if (user.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.CATEGORY_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.CATEGORY_NOT_FOUND.getMsg());
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
		userDAO.delete(user.get());
		LOG.error("[{}]: user {} was deleted", SERVICE_NAME, user.get());

	}

	@Override
	public Optional<User> login(String username, String password) {
		commonValidator.validateEmptyString(username, USERNAME_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(password, PASSWORD_KEY, SERVICE_NAME);
		Optional<User> user = userDAO.findByUsernameAndPassword(username, password);
		return user.isPresent() ? Optional.ofNullable(user.get()) : Optional.empty();
	}

	@Override
	public Optional<User> getByName(String name) {
		commonValidator.validateEmptyString(name, NAME_KEY, SERVICE_NAME);
		return userDAO.findByName(name);
	}

	protected void validateUser(User user) throws AbuMazrouqDashboardException {
		commonValidator.validateEmptyObject(user, USER_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(user.getName(), NAME_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(user.getUsername(), USERNAME_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(user.getPassword(), PASSWORD_KEY, SERVICE_NAME);
		commonValidator.validateEmptyObject(user.getUserType(), USER_TYPE_KEY, SERVICE_NAME);
		Optional<User> byUsername = getByUsername(user.getUsername());

		if (byUsername.isPresent() && byUsername.get().getId() != user.getId()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.USER_ALREADY_EXIST,
					AbuMazrouqDashboardExceptionType.USER_ALREADY_EXIST.getMsg());
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Optional<User> getByUsername(String username) {
		commonValidator.validateEmptyString(username, USERNAME_KEY, SERVICE_NAME);
		return userDAO.findByUsername(username);
	}

	@Override
	public List<User> getByUserType(UserType userType) {
		commonValidator.validateEmptyObject(userType, USER_TYPE_KEY, SERVICE_NAME);
		return userDAO.findByUserType(userType);
	}

	@Override
	public List<User> getAllEmployee() {

		return getByUserType(UserType.EMPLOYEE);
	}

}
