package com.cts.hotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int HotelID;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100)
	private String location;

	@Column(nullable = false)
	private int managerID;

	@Column
	private String amenities;

	@Column(nullable = false)
	private double rating;

	public int getHotelID() {
		return HotelID;
	}

	public void setHotelID(int hotelID) {
		HotelID = hotelID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public double getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return "Hotel [HotelID=" + HotelID + ", name=" + name + ", location=" + location + ", managerID=" + managerID
				+ ", amenities=" + amenities + ", rating=" + rating + "]";
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
