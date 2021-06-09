/*
   Copyright 2009-2021 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.mbaker.abumazrouqdashboard.morpheus.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.util.Strings;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import org.primefaces.model.file.UploadedFile;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.CategoryService;
import com.mbaker.abumazrouqdashboard.services.ItemService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@RequestScoped
public class ItemsView implements Serializable {
	
	
	private final static String ERROR_MSG = "item.msg.failed";

	private List<Item> items;

	private LazyDataModel<Item> lazyItems;

	private Item selectedItem;

	private List<Item> selectedItems;
	
    private UploadedFile file;

	@Inject
	private ItemService itemService;

	private Category category;

	@Inject
	private CategoryService categoryService;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	@Inject
	private ResourceBundle bundle;

	public void init() throws AbuMazrouqDashboardException {
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String id = requestParameterMap.get("id");
		if(Strings.isBlank(id)) {
			FacesUtils.redirect("categories");
			return;
		}
		Optional<Category> categoryById = categoryService.getById(Long.valueOf(id));
		if (categoryById.isPresent()) {
			this.category = categoryById.get();
		}else {
			FacesUtils.redirect("categories");
		}
		// lazyItems = new
		// LazyItemDataModel(itemService.getByCategory(category.getId()));
	}

	
	
	public List<Item> getItems() throws AbuMazrouqDashboardException {
		items = itemService.getByCategory(this.category.getId());
		return items;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<Item> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<Item> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LazyDataModel<Item> getLazyItems() {
		return lazyItems;
	}

	public void setLazyItems(LazyDataModel<Item> lazyItems) {
		this.lazyItems = lazyItems;
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	

	
	public void editItem() {
		FacesUtils.redirect("addEditItem");
	}
	
	
	
	public void openNew() {
		Item item = new Item();
		item.setCategory(category);
		this.selectedItem = item;
		FacesUtils.redirect("addEditItem");
	}

	public void saveItem() {
		
		
		if(file==null) {
			System.out.println("file is null");
		}else {
			System.out.println("file is"+file.getFileName() );
		}
		
		try {
			if (this.selectedItem.getId() == 0) {

				itemService.save(selectedItem);
				FacesUtils.sucessMessage(bundle.getString("item.msg.add.success"));
			} else {
				itemService.save(selectedItem);
				FacesUtils.sucessMessage(bundle.getString("item.msg.update.success"));
			}
			PrimeFaces.current().executeScript("PF('manageItemDialog').hide()");
		} catch (Exception e) {

			FacesUtils.errorMessage(bundle.getString(ERROR_MSG));

		}

		PrimeFaces.current().ajax().update("form:messages", "form:dt-Items");

	}
	
	
	public void upload() {
		if(file!=null)
	System.out.println(file.getFileName());
		
		
	}

	public void deleteItem() {

		try {
			itemService.delete(selectedItem.getId());
		} catch (AbuMazrouqDashboardException e) {
			FacesUtils.errorMessage(bundle.getString(ERROR_MSG));
		}
		this.selectedItem = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(bundle.getString("item.msg.delete.success")));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-Items");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedItems()) {
			int size = this.selectedItems.size();
			return size > 1 ? size + " Items selected" : "1 Item selected";
		}

		return "Delete";
	}

	public boolean hasSelectedItems() {
		return this.selectedItems != null && !this.selectedItems.isEmpty();
	}

	public void onRowToggle(ToggleEvent event) {
		if (event.getVisibility() == Visibility.VISIBLE) {

		}
	}
	
	

}
