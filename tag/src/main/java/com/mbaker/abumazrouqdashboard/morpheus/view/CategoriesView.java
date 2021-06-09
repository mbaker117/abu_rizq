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
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.CategoryService;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@ViewScoped
public class CategoriesView implements Serializable {
	private final static String ERROR_MSG = "category.msg.failed";

	private List<Category> categorys;

	private Category selectedCategory;

	private List<Category> selectedCategories;

	@Inject
	private CategoryService categoryService;

	/**
	 * the bundle variable of type ResourceBundle
	 */
	@Inject
	private ResourceBundle bundle;

	public List<Category> getCategories() {
		categorys = categoryService.getAll();
		return categorys;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<Category> getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(List<Category> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public void openNew() {

		this.selectedCategory = new Category();

	}

	public void saveCategory() {
		try {
			if (this.selectedCategory.getId() == 0) {

				categoryService.save(selectedCategory);
				FacesUtils.sucessMessage(bundle.getString("category.msg.add.success"));
			} else {
				categoryService.save(selectedCategory);
				FacesUtils.sucessMessage(bundle.getString("category.msg.update.success"));
			}
			PrimeFaces.current().executeScript("PF('manageCategoryDialog').hide()");
		} catch (Exception e) {

			FacesUtils.errorMessage(bundle.getString(ERROR_MSG));

		}

		PrimeFaces.current().ajax().update("form:messages", "form:dt-Categories");

	}

	public void deleteCategory() {

		try {
			categoryService.delete(selectedCategory.getId());
		} catch (AbuMazrouqDashboardException e) {
			FacesUtils.errorMessage(bundle.getString(ERROR_MSG));
		}
		this.selectedCategory = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(bundle.getString("category.msg.delete.success")));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-Categories");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedCategories()) {
			int size = this.selectedCategories.size();
			return size > 1 ? size + " Categories selected" : "1 Category selected";
		}

		return "Delete";
	}

	public boolean hasSelectedCategories() {
		return this.selectedCategories != null && !this.selectedCategories.isEmpty();
	}

	public void onRowToggle(ToggleEvent event) {
		if (event.getVisibility() == Visibility.VISIBLE) {

		}
	}
	
	public void showItems() {
		
		
		/*
		 * FacesContext context = FacesContext.getCurrentInstance();
		 * context.getExternalContext().getSessionMap().put("categoryId",
		 * selectedCategory.getId());
		 */
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "/users");
		FacesUtils.redirect("items");
		
	}
	
	/*
	 * public String showItems() { return "users.xhtml";
	 * 
	 * }
	 */
}
