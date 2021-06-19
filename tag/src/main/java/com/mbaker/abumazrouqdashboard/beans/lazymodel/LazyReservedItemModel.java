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
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;

public class LazyReservedItemModel extends  LazyDataModel<ReservedItemData>{
	
	 private List<ReservedItemData> datasource;
	 
	 
	 public LazyReservedItemModel(List<ReservedItemData> datasource) {
	        this.datasource = datasource;
	    }
	 
	   @Override
	    public ReservedItemData getRowData(String rowKey) {
	        for (ReservedItemData user : datasource) {
	            if (user.getId() == Integer.parseInt(rowKey)) {
	                return user;
	            }
	        }

	        return null;
	    }

	    @Override
	    public String getRowKey(ReservedItemData user) {
	        return String.valueOf(user.getId());
	    }

	    @Override
	    public List<ReservedItemData> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
	        long rowCount = datasource.stream()
	                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
	                .count();

	        // apply offset & filters
	        List<ReservedItemData> reservedItems = datasource.stream()
	                .skip(offset)
	                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
	                .limit(pageSize)
	                .collect(Collectors.toList());

	        // sort
	        if (!sortBy.isEmpty()) {
	            List<Comparator<ReservedItemData>> comparators = sortBy.values().stream()
	                    .map(o -> new LazySorter<ReservedItemData>(o.getField(), o.getOrder()))
	                    .collect(Collectors.toList());
	            Comparator<ReservedItemData> cp = ComparatorUtils.chainedComparator(comparators); // from apache
	            reservedItems.sort(cp);
	        }

	        // rowCount
	        setRowCount((int) rowCount);

	        return reservedItems;
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