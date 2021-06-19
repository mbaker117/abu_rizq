package com.mbaker.abumazrouqdashboard.facade.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

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
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;
import com.mbaker.abumazrouqdashboard.facade.ReservationFacade;
import com.mbaker.abumazrouqdashboard.services.ItemService;
import com.mbaker.abumazrouqdashboard.services.ReservationService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Component
public class DefaultReservationFacade implements ReservationFacade {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultReservationFacade.class);

	private final static String SERVICE_NAME = "ReservationFacade";
	private final static String LOG_MSG = "[ReservationFacade]: {}";
	private final static String RESERVATION_KEY = "reservation";

	@Autowired
	private CommonValidator commonValidator;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ReservedItemDataConvertor reservedItemConvertor;

	@Override
	public List<ReservedItemData> getReservedItemByDates(Date startDate, Date endDate) {
		List<Reservation> reservations = reservationService.getByDatesAndStatus(startDate, endDate,
				ReservationStatus.PENDING);
		return getAvailableItem(reservations);
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
				if (totalItemsCount.containsKey(reservedItem.getItemId())) {
					Long value = totalItemsCount.get(reservedItem.getItemId());
					totalItemsCount.put(reservedItem.getItemId(), value + reservedItem.getQuantity());
				} else {
					totalItemsCount.put(reservedItem.getItemId(), reservedItem.getQuantity());
				}
			}

		}

		List<Item> all = itemService.getAll();

		if (CollectionUtils.isEmpty(all)) {
			return null;
		}

		List<Item> items = new ArrayList<Item>();

		/*
		 * all.stream().filter(i -> i.getQuantity() > totalItemsCount.get(i.getId()))
		 * .collect(Collectors.toList());
		 */
		for (Item i : all) {

			if (totalItemsCount.containsKey(i.getId()) && i.getQuantity() <= (long) totalItemsCount.get(i.getId())) {
				continue;
			} else {
				items.add(i);
			}
		}
		List<ReservedItemData> data = new ArrayList<ReservedItemData>();

		for (Item item : items) {
			ReservedItemData reservedItemData = reservedItemConvertor.convert(item);
			if (totalItemsCount.containsKey(item.getId())) {
				reservedItemData.setAvailableAmount(item.getQuantity() - (long) totalItemsCount.get(item.getId()));
			} else {
				reservedItemData.setAvailableAmount(item.getQuantity());

			}
			data.add(reservedItemData);
		}

		return data;
	}

	@Override
	public List<ReservedItemData> getReservedItemByReservation(Reservation reservation) {
		commonValidator.validateEmptyObject(reservation, LOG_MSG, SERVICE_NAME);
		List<ReservedItemData> reservedItemByDates = getReservedItemByDates(reservation.getDate(),
				reservation.getDate());
		List<ReservedItem> items = reservation.getItems();

		Map<Long, ReservedItemData> reservedMap = new LinkedHashMap<Long, ReservedItemData>();
		for (ReservedItemData data : reservedItemByDates) {
			reservedMap.put(data.getItemId(), data);
		}
		List<ReservedItemData> data = new ArrayList<ReservedItemData>();
		for (ReservedItem item : items) {
			ReservedItemData reservedItemData;
			if (reservedMap.containsKey(item.getItemId())) {

				reservedItemData = reservedMap.get(item.getItemId());
				if (reservedItemData.getAvailableAmount() < reservedItemData.getTotalAmount()) {
					reservedItemData.setAvailableAmount(reservedItemData.getAvailableAmount() + item.getQuantity());
				}
			} else {
				Optional<Item> itembyId = itemService.getById(item.getItemId());
				if (itembyId.isEmpty()) {
					continue;
				}
				reservedItemData = reservedItemConvertor.convert(itembyId.get());
				reservedItemData.setAvailableAmount(item.getQuantity());

			}
			reservedItemData.setId(item.getId());
			reservedItemData.setNotes(item.getNotes());
			reservedItemData.setReservedAmount(item.getQuantity());
			reservedMap.put(item.getItemId(), reservedItemData);
		}

		for (Entry<Long, ReservedItemData> entry : reservedMap.entrySet()) {
			data.add(entry.getValue());
		}
		Collections.sort(data, Collections.reverseOrder());
		return data;
	}

	private List<ReservedItemData> convertMapToList(Map map) {

		return null;
	}

}
