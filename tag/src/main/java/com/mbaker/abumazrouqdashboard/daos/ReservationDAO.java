package com.mbaker.abumazrouqdashboard.daos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;

@Repository
@EnableJpaRepositories
public interface ReservationDAO extends JpaRepository<Reservation, Long> {

	public Optional<Reservation> findByEmployeeName(String employeeName);

	@Query("SELECT r FROM Reservation r WHERE r.date>= ?1 and r.date<= ?2")
	public List<Reservation> findByDates(Date startDate, Date endDate);

	@Query("SELECT r FROM Reservation r WHERE r.date>= ?1 and r.date<= ?2")
	public List<Reservation> findByDatesAndStatus(Date startDate, Date endDate, ReservationStatus status);
}