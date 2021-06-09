package com.mbaker.abumazrouqdashboard.services;

import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.Admin;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface AdminService {
	
	public Optional<Admin> login(String username, String password);
	
	public List<Admin> findAll();
	
	public Optional<Admin> findById(long id);
	
	public void save(Admin admin);
	
	public void delete(long id) throws AbuMazrouqDashboardException;
}
