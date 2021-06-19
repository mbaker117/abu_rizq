package com.mbaker.abumazrouqdashboard.daos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mbaker.abumazrouqdashboard.beans.model.Reservation;
import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;

@Repository
@EnableJpaRepositories
public interface ReservationDAO extends JpaRepository<Reservation, Long> {

	public List<Reservation> findByEmployeeName(String employeeName);

	public List<Reservation> findByEmployeeId(long employeeId);

	@Query("SELECT r FROM Reservation r WHERE r.date>= ?1 and r.date<= ?2")
	public List<Reservation> findByDates(Date startDate, Date endDate);

	@Query("SELECT r FROM Reservation r WHERE r.date>= ?1 and r.date<= ?2 and r.status =?3")
	public List<Reservation> findByDatesAndStatus(Date startDate, Date endDate, ReservationStatus status);
	
	@Query("SELECT r FROM Reservation r WHERE r.date>= ?1 and r.date<= ?2 and r.employeeName Like %?3%")
	public List<Reservation> findByDatesAndEmployeeName(Date startDate, Date endDate, String employeeName);
}
