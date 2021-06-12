package com.mbaker.abumazrouqdashboard.beans.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "RESERVED_ITEM")
public class ReservedItemData {

	@Id
	@SequenceGenerator(name = "reserved_item_generator", sequenceName = "reserved_item_generator", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserved_item_generator")
	@NotNull
	@Column(name = "id")
	private long id;

	private long itemId;

	private String name;

	private String owner;

	private long availableAmount;
	
	private long totalAmount;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reservation_id", nullable = true)
	private Reservation reservation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	

	public long getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(long availableAmount) {
		this.availableAmount = availableAmount;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}
