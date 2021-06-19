package com.mbaker.abumazrouqdashboard.beans.lazymodel;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

import org.apache.commons.collections.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

import com.mbaker.abumazrouqdashboard.beans.model.Category;

public class LazyCategoryDataModel extends  LazyDataModel<Category>{
	
	 private List<Category> datasource;
	 
	 
	 public LazyCategoryDataModel(List<Category> datasource) {
	        this.datasource = datasource;
	    }
	 
	   @Override
	    public Category getRowData(String rowKey) {
	        for (Category Categorie : datasource) {
	            if (Categorie.getId() == Integer.parseInt(rowKey)) {
	                return Categorie;
	            }
	        }

	        return null;
	    }

	    @Override
	    public String getRowKey(Category Categorie) {
	        return String.valueOf(Categorie.getId());
	    }

	    @Override
	    public List<Category> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
	        long rowCount = datasource.stream()
	                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
	                .count();

	        // apply offset & filters
	        List<Category> Categories = datasource.stream()
	                .skip(offset)
	                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
	                .limit(pageSize)
	                .collect(Collectors.toList());

	        // sort
	        if (!sortBy.isEmpty()) {
	            List<Comparator<Category>> comparators = sortBy.values().stream()
	                    .map(o -> new LazySorter<Category>(o.getField(), o.getOrder()))
	                    .collect(Collectors.toList());
	            Comparator<Category> cp = ComparatorUtils.chainedComparator(comparators); // from apache
	            Categories.sort(cp);
	        }

	        // rowCount
	        setRowCount((int) rowCount);

	        return Categories;
	    }


	    private boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
	        boolean matching = true;

	        for (FilterMeta filter : filterBy) {
	            FilterConstraint constraint = filter.getConstraint();
	            Object filterValue = filter.getFilterValue();

	            try {
	                Object columnValue = String.valueOf(o.getClass().getField(filter.getField()).get(o));
	                matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
	            } catch (ReflectiveOperationException e) {
	                matching = false;
	            }

	            if (!matching) {
	                break;
	            }
	        }

	        return matching;
	    }
	}