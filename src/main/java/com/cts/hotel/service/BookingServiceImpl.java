package com.cts.hotel.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.exception.BadRequestException;
import com.cts.hotel.exception.BookingNotFoundException;
import com.cts.hotel.exception.ResourceNotFoundException;
import com.cts.hotel.model.Booking;
import com.cts.hotel.model.BookingStatus;
import com.cts.hotel.repo.BookingRepository;

@Service
public class BookingServiceImpl {

	@Autowired
	private BookingRepository bookingRepository;

	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	public ResponseEntity<Booking> getById(int bookingId) {
		Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
		return optionalBooking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(422).body(null));
	}

	public Booking bookRoom(Booking booking) {
		if (booking.getCheckInDate() == null || booking.getCheckOutDate() == null) {
			throw new BadRequestException("Check-in and check-out dates must not be null.");
		}

		List<Booking> conflicts = bookingRepository.findConflictingBookings(booking.getRoomId(),
				booking.getCheckInDate(), booking.getCheckOutDate());
		if (!conflicts.isEmpty()) {
			throw new BadRequestException("Room is already booked.");
		}

		booking.setStatus(BookingStatus.PENDING);
		return bookingRepository.save(booking);
	}

	public void cancelBooking(int id) {
		// TODO Auto-generated method stub
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime checkIn = booking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		long hoursUntilCheckIn = Duration.between(now, checkIn).toHours();

		if (hoursUntilCheckIn < 24) {
			throw new BadRequestException("Cannot cancel booking less than 24 hours before check-in.");
		}

		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepository.save(booking);
	}

	public Booking updateBooking(int oldBookingId, Booking newBooking) {
		Optional<Booking> optionalExisting = bookingRepository.findById(oldBookingId);
		if (optionalExisting.isEmpty()) {
			// Throw your custom exception
			throw new BookingNotFoundException("Booking not found with ID: " + oldBookingId);
		}

		Booking existing = optionalExisting.get();

		if (newBooking.getUserID() != 0)
			existing.setUserID(newBooking.getUserID());

		if (newBooking.getRoomId() != 0)
			existing.setRoomId(newBooking.getRoomId());

		if (newBooking.getCheckInDate() != null)
			existing.setCheckInDate(newBooking.getCheckInDate());

		if (newBooking.getCheckOutDate() != null)
			existing.setCheckOutDate(newBooking.getCheckOutDate());

		if (newBooking.getStatus() != null)
			existing.setStatus(newBooking.getStatus());

		if (newBooking.getPaymentID() != 0)
			existing.setPaymentID(newBooking.getPaymentID());

		return bookingRepository.save(existing);
	}
}
