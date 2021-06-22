package com.mbaker.abumazrouqdashboard.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.mbaker.abumazrouqdashboard.beans.model.Admin;
import com.mbaker.abumazrouqdashboard.daos.AdminDAO;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.AdminService;

@Service
public class DefaultAdminService implements AdminService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultAdminService.class);

	@Autowired
	private AdminDAO adminDAO;

	@Override
	public Optional<Admin> login(String username, String password) {
		if (Strings.isBlank(username)) {
			final IllegalArgumentException ex = new IllegalArgumentException("username is null or empty");
			LOG.error("[AdminService]: {}", ex.getMessage());
			throw ex;
		}
		

		if (Strings.isBlank(password)) {
			final IllegalArgumentException ex = new IllegalArgumentException("password is null or empty");
			LOG.error("[AdminService]: {}", ex.getMessage());
			throw ex;
		}
		Optional<Admin> admin = adminDAO.findByUsernameAndPassword(username, password);
		return admin.isPresent() ? Optional.ofNullable(admin.get()) : Optional.empty();
	}

	@Override
	public List<Admin> findAll() {

		return adminDAO.findAll();
	}

	@Override
	public Optional<Admin> findById(long id) {
		return adminDAO.findById(id);
	}

	@Override
	public void save(Admin admin) {
		if (Objects.isNull(admin)) {
			final IllegalArgumentException ex = new IllegalArgumentException("admin is null");
			LOG.error("[AdminService]: {}", ex.getMessage());
			throw ex;
		}
		adminDAO.save(admin);
	}

	@Override
	public void delete(long id) throws AbuMazrouqDashboardException {

		Optional<Admin> admin = findById(id);

		if (admin.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.ADMIN_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.ADMIN_NOT_FOUND.getMsg());
			LOG.error("[AdminService]: {}", ex.getMessage());
			throw ex;
		}

		adminDAO.delete(admin.get());

	}

}
