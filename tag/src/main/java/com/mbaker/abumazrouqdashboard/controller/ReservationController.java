package com.mbaker.abumazrouqdashboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@GetMapping
	public List<Reservation> findAll(){
		return reservationService.findAll();
	}
	
	@GetMapping("/{id}")
	public Reservation findById(@PathVariable("id") long id){
		return reservationService.findById(id).get();
	}
	
	@PostMapping
	public void save(Reservation reservation) {
		reservationService.save(reservation);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id")long id) throws AbuMazrouqDashboardException{
		reservationService.delete(id);
	}

}
