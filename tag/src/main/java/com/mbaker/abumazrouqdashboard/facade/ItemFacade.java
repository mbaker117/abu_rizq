package com.mbaker.abumazrouqdashboard.facade;

import java.util.List;

import org.primefaces.model.file.UploadedFile;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;

public interface ItemFacade {
	
	public void saveItem(Item item, UploadedFile file) throws AbuMazrouqDashboardException;
	
	public void deleteItem(long id) throws AbuMazrouqDashboardException;

	public List<Item> getItemByCategoryId(long id) throws AbuMazrouqDashboardException;


}
