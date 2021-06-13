package com.mbaker.abumazrouqdashboard.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.beans.model.ReservedItemData;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.facade.ReservationFacade;
import com.mbaker.abumazrouqdashboard.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ReservationFacade reservationFacade;
	
	
	@GetMapping
	public List<Reservation> findAll(){
		return reservationService.getAll();
	}
	
	@GetMapping("/{id}")
	public Reservation findById(@PathVariable("id") long id){
		return reservationService.getById(id).get();
	}
	
	@PostMapping
	public void add(@RequestBody Reservation reservation) {
		reservationService.add(reservation);
	}
	
	@PutMapping
	public void update(Reservation reservation) {
		reservationService.update(reservation);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id")long id) throws AbuMazrouqDashboardException{
		reservationService.delete(id);
	}
	
	
	
	@PostMapping("/av/items")
	public List<ReservedItemData> getItems(@RequestBody Date startDate, ReservationStatus status){
		return reservationFacade.getReservedItemByDates(startDate, startDate);
	}
	

}
