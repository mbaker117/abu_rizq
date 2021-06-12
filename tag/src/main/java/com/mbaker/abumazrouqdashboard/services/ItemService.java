package com.mbaker.abumazrouqdashboard.services;

import java.util.List;
import java.util.Optional;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface ItemService {

	public List<Item> getAll();
	
	public List<Item> getAll(int pageNumber, int pageSize, String sortBy);
	
	public Optional<Item> getById(long id);

	public void save(Item item);

	public void delete(long id) throws AbuMazrouqDashboardException;

	public Optional<Item> getByName(String name);

	public List<Item> getByCategory(long categoryId) throws AbuMazrouqDashboardException ;
	
}
