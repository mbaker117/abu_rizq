package com.mbaker.abumazrouqdashboard.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.daos.CategoryDAO;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.exception.type.AbuMazrouqDashboardExceptionType;
import com.mbaker.abumazrouqdashboard.services.CategoryService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Service
public class DefaultCategoryService implements CategoryService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultAdminService.class);
	private final static String SERVICE_NAME = "CategoryService";
	private final static String LOG_MSG = "[CategoryService]: {}";
	private final static String NAME_KEY = "name";
	private final static String CATEGORY_KEY = "category";
	
	@Autowired
	private CommonValidator commonValidator;
	
	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public List<Category> getAll() {

		return categoryDAO.findAll();
	}

	@Override
	public Optional<Category> getById(long id) {
		return categoryDAO.findById(id);
	}

	@Override
	public void save(Category category) {
		if (Objects.isNull(category)) {
			final IllegalArgumentException ex = new IllegalArgumentException("category is null");
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
		categoryDAO.save(category);
	}

	@Override
	public void delete(long id) throws AbuMazrouqDashboardException {

		Optional<Category> category = getById(id);
		if (category.isEmpty()) {
			var ex = new AbuMazrouqDashboardException(AbuMazrouqDashboardExceptionType.CATEGORY_NOT_FOUND,
					AbuMazrouqDashboardExceptionType.CATEGORY_NOT_FOUND.getMsg());
			LOG.error(LOG_MSG, ex.getMessage());
			throw ex;
		}
		
		categoryDAO.delete(category.get());
	}
	
	protected void validateCategory(Category category) {
		commonValidator.validateEmptyObject(category, CATEGORY_KEY, SERVICE_NAME);
		commonValidator.validateEmptyString(category.getName(), NAME_KEY, SERVICE_NAME);
	}

}
