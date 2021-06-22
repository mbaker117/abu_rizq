package com.mbaker.abumazrouqdashboard.convertors;

import java.io.FileNotFoundException;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.data.ItemData;
import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.utils.FileUtil;

@Component
public class ItemConvertor extends Convertor<Item, ItemData> {

	@Override
	public ItemData convert(Item t) {
		ItemData item = new ItemData();
		item.setId(t.getId());
		item.setName(t.getName());
		item.setQuantity(t.getQuantity());
		item.setOwner(t.getOwner());
		item.setImageUrl(t.getImageUrl());
		item.setCategory(t.getCategory());
		StreamedContent imageFromPath;
		try {
			imageFromPath = FileUtil.getImageFromPath(t.getImageUrl());
			item.setImage(imageFromPath);
			

		} catch (FileNotFoundException e) {

		}
		return item;
	}

}
