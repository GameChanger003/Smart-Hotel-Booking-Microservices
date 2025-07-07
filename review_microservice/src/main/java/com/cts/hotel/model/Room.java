package com.cts.hotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roomID;

	@Column
	private int hotelID;

	@Column(nullable = false, length = 50)
	private String type;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private String availability;

	@Column(length = 500)
	private String features;

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "Room [roomID=" + roomID + ", hotelID=" + hotelID + ", type=" + type + ", price=" + price
				+ ", availability=" + availability + ", features=" + features + "]";
	}

	public Room(int roomID, int hotelID, String type, int price, String availability, String features) {
		super();
		this.roomID = roomID;
		this.hotelID = hotelID;
		this.type = type;
		this.price = price;
		this.availability = availability;
		this.features = features;
	}

	public Room() {
		super();
	}
	

}
