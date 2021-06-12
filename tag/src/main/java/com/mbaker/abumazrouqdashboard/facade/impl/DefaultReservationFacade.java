package com.mbaker.abumazrouqdashboard.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItem;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;
import com.mbaker.abumazrouqdashboard.convertors.ReservedItemDataConvertor;
import com.mbaker.abumazrouqdashboard.facade.ReservationFacade;
import com.mbaker.abumazrouqdashboard.services.ItemService;
import com.mbaker.abumazrouqdashboard.services.ReservationService;

@Component
public class DefaultReservationFacade implements ReservationFacade {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultReservationFacade.class);

	private final static String SERVICE_NAME = "ReservationFacade";
	private final static String LOG_MSG = "[ReservationFacade]: {}";

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ReservedItemDataConvertor reservedItemConvertor;

	@Override
	public List<ReservedItem> getReservedItemByDates(Date startDate, Date endDate) {
		List<Reservation> reservations = reservationService.getByDates(startDate, endDate);
		getAvailableItem(reservations);
		return null;
	}

	private List<ReservedItemData> getAvailableItem(List<Reservation> reservations) {
		if (CollectionUtils.isEmpty(reservations)) {
			return reservedItemConvertor.convertAll(itemService.getAll());
		}
		Map<Long, Long> totalItemsCount = new LinkedHashMap<>();
		for (Reservation reservation : reservations) {
			if (CollectionUtils.isEmpty(reservation.getItems())) {
				continue;
			}
			for (ReservedItem reservedItem : reservation.getItems()) {
				if (totalItemsCount.containsKey(reservedItem.getId())) {
					Long value = totalItemsCount.get(reservedItem.getId());
					totalItemsCount.put(reservedItem.getId(), value + reservedItem.getQuantity());
				} else {
					totalItemsCount.put(reservedItem.getId(), 0l);
				}
			}
		}

		List<Item> all = itemService.getAll();

		if (CollectionUtils.isEmpty(all)) {
			return null;
		}

		all.stream().filter(i -> i.getQuantity() > totalItemsCount.get(i.getId()))
				.map(i -> i.getQuantity() - totalItemsCount.get(i.getId())).collect(Collectors.toList());

		List<Item> items = all.stream().filter(i -> i.getQuantity() > totalItemsCount.get(i.getId()))
				.collect(Collectors.toList());
		List<ReservedItemData> data = new ArrayList<ReservedItemData>();

		for (Item item : items) {
			ReservedItemData reservedItemData = reservedItemConvertor.convert(item);
			reservedItemData.setAvailableAmount(item.getQuantity() - totalItemsCount.get(item.getId()));
			data.add(reservedItemData);
		}

		return data;
	}

	private List<ReservedItem> getReserverItems(List<Item> all) {

		return null;
	}

}
