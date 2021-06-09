package com.mbaker.abumazrouqdashboard.services;

import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface CategoryService {
	
	public List<Category> getAll();
	
	public Optional<Category> getById(long id);
	
	public void save(Category category);
	
	public void delete(long id) throws AbuMazrouqDashboardException;
	
}
