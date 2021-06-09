package com.mbaker.abumazrouqdashboard.beans.lazymodel;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

public class  LazySorter <K>  implements Comparator<K> {
	
    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

	@Override
	public int compare(K o1, K o2) {
		try {
            Object value1 = o1.getClass().getField(sortField).get(o1);
            Object value2 = o2.getClass().getField(sortField).get(o2);

            int value = ((Comparable)value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
	}
	

}
