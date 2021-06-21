package com.mbaker.abumazrouqdashboard.facade.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.constant.PathConstant;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.facade.ItemFacade;
import com.mbaker.abumazrouqdashboard.services.FileService;
import com.mbaker.abumazrouqdashboard.services.ItemService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Component
public class DefaultItemFacade implements ItemFacade {

	private final static Logger LOG = LoggerFactory.getLogger(ItemFacade.class);
	private final static String SERVICE_NAME = "ItemService";
	private final static String LOG_MSG = "[ItemService]: {}";
	private final static String ITEM_KEY = "item";
	private final static String FILE_KEY = "file";

	@Autowired
	private ItemService itemService;

	@Autowired
	private FileService fileService;

	@Autowired
	private CommonValidator commonValidator;

	private static final String PATH = "/home/ubuntu/abumazrouqdashboard/tag/src/main/webapp/resources/items/images";

	@Override
	public void saveItem(Item item, UploadedFile file) throws AbuMazrouqDashboardException {
		commonValidator.validateEmptyObject(item, ITEM_KEY, SERVICE_NAME);
		if (file != null && Strings.isNotBlank(file.getFileName())) {
			itemService.save(item);
			String fileName = file.getFileName();
			int lastIndexOf = fileName.lastIndexOf('.');
			String substring = fileName.substring(lastIndexOf, fileName.length());
			String imageName = "";
			try {
				imageName = fileService.UploadFile(PATH, item.getId() + substring, file);
			} catch (IOException e) {
				e.printStackTrace();
				var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.IO_EXCEPTION,
						e.getMessage());
				LOG.error(LOG_MSG, ex.getMessage());
				throw ex;
			}
			
			item.setImageUrl(imageName);
		}
		itemService.save(item);
	}

	@Override
	public List<Item> getItemByCategoryId(long id) throws AbuMazrouqDashboardException {

		return itemService.getByCategory(id);
	}

	@Override
	public void deleteItem(long id) throws AbuMazrouqDashboardException {
		itemService.delete(id);

	}

}
