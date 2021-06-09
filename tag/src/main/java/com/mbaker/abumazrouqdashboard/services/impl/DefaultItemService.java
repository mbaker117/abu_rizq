package com.mbaker.abumazrouqdashboard.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.daos.ItemDAO;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.CategoryService;
import com.mbaker.abumazrouqdashboard.services.ItemService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Service
public class DefaultItemService implements ItemService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultItemService.class);
	private final static String SERVICE_NAME = "ItemService";
	private final static String LOG_MSG = "[ItemService]: {}";
	private final static String NAME_KEY = "name";
	private final static String QUANTITY_KEY = "quantity";
	private final static String CATEGORY_KEY = "category";
	private final static String ITEM_KEY = "item";
	private final static String OWNER_KEY = "owner";

	@Autowired
	private CommonValidator commonValidator;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<Item> getAll() {

		return itemDAO.findAll();
	}

	@Override
	public Optional<Item> getById(long id) {
		return itemDAO.findById(id);
	}

	@Override
	public void save(Item item) {
		validateItem(item);
		itemDAO.save(item);
		LOG.error("[ItemService]: item {} was added", item);
	}

	@Override
	public void delete(long id) throws AbuMazrouqDashboardException {
		Optional<Item> item = getById(id);
		if (item.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.ITEM_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.ITEM_NOT_FOUND.getMsg());
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
		itemDAO.delete(item.get());
		LOG.error("[ItemService]: item {} was deleted", item.get());

	}

	@Override
	public Optional<Item> getByName(String name) {
		commonValidator.validateEmptyString(name, NAME_KEY, SERVICE_NAME);
		return itemDAO.findByName(name);
	}

	@Override
	public List<Item> getByCategory(long categoryId) throws AbuMazrouqDashboardException {

		Optional<Category> categoryById = categoryService.getById(categoryId);
		if (categoryById.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.CATEGORY_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.CATEGORY_NOT_FOUND.getMsg());
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}

		return itemDAO.findByCategory(categoryById.get());
	}

	protected void validateItem(Item item) {
		commonValidator.validateEmptyObject(item, ITEM_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(item.getName(), NAME_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(item.getOwner(), OWNER_KEY, SERVICE_NAME);
		commonValidator.validateEmptyObject(item.getCategory(), CATEGORY_KEY, SERVICE_NAME);
		if (item.getQuantity() <= 0) {
			var ex = new IllegalArgumentException("invalid " + QUANTITY_KEY);
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<Item> getAll(int pageNumber, int pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Item> result = itemDAO.findAll(paging);

		return result.hasContent() ? result.getContent() : Collections.emptyList();
	}

}
