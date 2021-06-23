package com.mbaker.abumazrouqdashboard.convertors;

import java.io.FileNotFoundException;
import java.util.Objects;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;
import com.mbaker.abumazrouqdashboard.utils.FileUtil;

@Component
public class ReservedItemDataConvertor extends Convertor<Item, ReservedItemData> {

	@Override
	public ReservedItemData convert(Item t) {
		if (Objects.isNull(t)) {
			return null;
		}
		ReservedItemData item = new ReservedItemData();
		/* item.setId(t.getId()); */
		item.setItemId(t.getId());
		item.setName(t.getName());
		item.setAvailableAmount(t.getQuantity());
		item.setTotalAmount(t.getQuantity());
		item.setOwner(t.getOwner());
		item.setImageUrl(t.getImageUrl());
		/*
		 * StreamedContent imageFromPath; try { imageFromPath =
		 * FileUtil.getImageFromPath(t.getImageUrl()); item.setImage(imageFromPath);
		 * 
		 * } catch (FileNotFoundException e) {
		 * 
		 * }
		 */
		return item;

	}

}