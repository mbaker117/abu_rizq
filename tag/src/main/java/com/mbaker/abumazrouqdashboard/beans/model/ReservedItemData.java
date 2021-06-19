package com.mbaker.abumazrouqdashboard.beans.model;

public class ReservedItemData implements Comparable<ReservedItemData> {

	private long id;

	private long itemId;

	private String name;

	private String owner;

	private long availableAmount;

	private long totalAmount;

	private long reservedAmount;

	private String imageUrl;

	private String notes;

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

	public long getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(long reservedAmount) {
		this.reservedAmount = reservedAmount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public int compareTo(ReservedItemData o) {
		Long l1 = Long.valueOf(this.reservedAmount);
		Long l2 = Long.valueOf(o.reservedAmount);
		return l1.compareTo(l2);
	}

}
