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

import com.mbaker.abumazrouqdashboard.beans.model.Item;

public class LazyItemDataModel extends  LazyDataModel<Item>{
	
	 private List<Item> datasource;
	 
	 
	 public LazyItemDataModel(List<Item> datasource) {
	        this.datasource = datasource;
	    }
	 
	   @Override
	    public Item getRowData(String rowKey) {
	        for (Item item : datasource) {
	            if (item.getId() == Integer.parseInt(rowKey)) {
	                return item;
	            }
	        }

	        return null;
	    }

	    @Override
	    public String getRowKey(Item item) {
	        return String.valueOf(item.getId());
	    }

	    @Override
	    public List<Item> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
	        long rowCount = datasource.stream()
	                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
	                .count();

	        // apply offset & filters
	        List<Item> items = datasource.stream()
	                .skip(offset)
	                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
	                .limit(pageSize)
	                .collect(Collectors.toList());

	        // sort
	        if (!sortBy.isEmpty()) {
	            List<Comparator<Item>> comparators = sortBy.values().stream()
	                    .map(o -> new LazySorter<Item>(o.getField(), o.getOrder()))
	                    .collect(Collectors.toList());
	            Comparator<Item> cp = ComparatorUtils.chainedComparator(comparators); // from apache
	            items.sort(cp);
	        }

	        // rowCount
	        setRowCount((int) rowCount);

	        return items;
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