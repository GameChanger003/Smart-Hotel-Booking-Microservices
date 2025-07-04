package com.cts.hotel.dao;

import java.util.List;

import com.cts.hotel.model.Hotel;

public interface HotelDAO {
	List<Hotel> getAllHotels();

	Hotel getHotelById(int id);

	Hotel addHotel(Hotel hotel);

	Hotel updateHotel(int oldHotelID, Hotel newHotel);

	void deleteHotel(int id);
}
