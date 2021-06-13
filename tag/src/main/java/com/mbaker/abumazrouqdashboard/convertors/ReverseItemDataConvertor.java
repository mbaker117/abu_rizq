package com.mbaker.abumazrouqdashboard.convertors;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.model.ReservedItem;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;

@Component
public class ReverseItemDataConvertor extends Convertor<ReservedItemData, ReservedItem > {

	@Override
	public ReservedItem convert(ReservedItemData t) {
		if (Objects.isNull(t)) {
			return null;
		}
		ReservedItem item = new ReservedItem();
		/* item.setId(t.getId()); */
		item.setItemId(t.getId());
		item.setName(t.getName());
		item.setQuantity(t.getReservedAmount());
		item.setOwner(t.getOwner());
		item.setNotes(t.getNotes());
		return item;
	}

}