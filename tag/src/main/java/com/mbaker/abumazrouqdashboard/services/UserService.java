package com.mbaker.abumazrouqdashboard.services;

import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface UserService {
	
	public List<User> getAll();
	
	public Optional<User> getById(long id);
	
	public void add(User user) throws AbuMazrouqDashboardException;
	
	public void update(User user) throws AbuMazrouqDashboardException;
	
	public void delete(long id) throws AbuMazrouqDashboardException;
	
	public Optional<User> login(String username, String password);

	public Optional<User> getByName(String name);
	
	public Optional<User> getByUsername(String username);


}
