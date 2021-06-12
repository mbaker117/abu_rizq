package com.mbaker.abumazrouqdashboard.convertors;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;

@Component
public class ReservedItemDataConvertor extends Convertor<Item, ReservedItemData> {

	@Override
	public ReservedItemData convert(Item t) {
		if(Objects.isNull(t)) {
			return null;
		}
		ReservedItemData item = new ReservedItemData();
		item.setItemId(t.getId());
		item.setName(t.getName());
		item.setAvailableAmount(t.getQuantity());
		item.setTotalAmount(t.getQuantity());
		item.setOwner(t.getOwner());
		return item;
	}

}