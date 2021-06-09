package com.mbaker.abumazrouqdashboard.convertors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public abstract class  Convertor <T,E>{
	
	abstract E convert(T t);
	
	public List<E> convertAll(List<T> models) {
		if (CollectionUtils.isEmpty(models)) {
			return Collections.emptyList();
		}
		List<E> data = new ArrayList<>();
		for (T model : models) {
			data.add(convert(model));
		}
		return data;
	}

}
