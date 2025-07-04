package com.cts.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.hotel.model.Booking;

public interface BookingService {
	
	Booking saveBooking(Booking booking);

    List<Booking> getAllBookings();

    ResponseEntity<Booking> getById(int bookingId);

    Booking bookRoom(Booking booking);

    void cancelBooking(int id);

    Booking updateBooking(int oldBookingId, Booking newBooking);

	

}
