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
import java.util.ResourceBundle;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.facade.ItemFacade;
import com.mbaker.abumazrouqdashboard.utils.FacesUtils;

@Named
@RequestScoped
public class AddEditItemView implements Serializable {

	private final static String ERROR_MSG = "item.msg.failed";

	private Item selectedItem;

	private UploadedFile file;
	
	@Inject
	private ItemFacade itemFacade;
	/**
	 * the bundle variable of type ResourceBundle
	 */
	@Inject
	private ResourceBundle bundle;

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void saveItem() {

		if (file == null) {
			System.out.println("file is null");
		} else {

			System.out.println("file is" + file.getFileName());
		}
		try {
			if (this.selectedItem.getId() == 0) {
				
				itemFacade.saveItem(selectedItem,file);
				FacesUtils.sucessMessage(bundle.getString("item.msg.add.success"));
			} else {
				itemFacade.saveItem(selectedItem,file);
				FacesUtils.sucessMessage(bundle.getString("item.msg.update.success"));
			}
			PrimeFaces.current().ajax().update("form:messages");

			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("id",
					String.valueOf(selectedItem.getCategory().getId()));
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getApplicationContextPath() + "/" + "items.xhtml?id="+selectedItem.getCategory().getId());
		} catch (Exception e) {

			FacesUtils.errorMessage(bundle.getString(ERROR_MSG));
		}
		PrimeFaces.current().ajax().update("form:messages");

	}

}
