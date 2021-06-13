package com.mbaker.abumazrouqdashboard.beans.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mbaker.abumazrouqdashboard.enums.ReservationStatus;
import com.sun.istack.NotNull;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

	@Id
	@SequenceGenerator(name = "reservation_generator", sequenceName = "reservation_generator", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_generator")
	@NotNull
	@Column(name = "id")
	private long id;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="reservation_id", nullable=false)
    private List<ReservedItem> items;

	private String notes;

	private String customerName;

	private String customerPhoneNumber;
	
	@Column(name = "Date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private String employeeName;
	
	private long employeeId;
	
	@Column(name = "status")
	@NotNull
	private ReservationStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<ReservedItem> getItems() {
		return items;
	}

	public void setItems(List<ReservedItem> items) {
		this.items = items;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
