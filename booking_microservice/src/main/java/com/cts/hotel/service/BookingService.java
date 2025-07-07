package com.cts.hotel.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.model.Booking;
import com.cts.hotel.model.BookingStatus;
import com.cts.hotel.repo.BookingRepository;

@Service
public class BookingService {

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
        return optionalBooking.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    public ResponseEntity<?> bookRoomIfAvailable(Booking booking) {
        if (booking.getCheckInDate() == null || booking.getCheckOutDate() == null) {
            return ResponseEntity.badRequest().body("Check-in and check-out dates must not be null");
        }

        if (booking.getCheckInDate().after(booking.getCheckOutDate())) {
            return ResponseEntity.badRequest().body("Check-in date must be before check-out date");
        }

        List<Booking> conflicts = bookingRepository.findConflictingBookings(
            booking.getRoomId(),
            booking.getCheckInDate(),
            booking.getCheckOutDate()
        );

        if (!conflicts.isEmpty()) {
            return ResponseEntity.status(409).body("Room is already booked for the selected dates.");
        }

        booking.setStatus(BookingStatus.PENDING);
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    public ResponseEntity<?> cancelBooking(int bookingId) {
        Booking booking = getById(bookingId).getBody();
        LocalDate now = LocalDate.now();
        LocalDate checkOut = booking.getCheckOutDate().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();

        long hoursUntilCheckout = ChronoUnit.HOURS.between(now.atStartOfDay(), checkOut.atStartOfDay());

        if (hoursUntilCheckout < 24) {
            return ResponseEntity.status(409).body("Cannot cancel within 24 hours of check-out.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    public Booking updateBooking(int oldBookingId, Booking newBooking) {
        Optional<Booking> optionalExisting = bookingRepository.findById(oldBookingId);
        if (optionalExisting.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + oldBookingId);
        }

        Booking existing = optionalExisting.get();

        if (newBooking.getUserID() != 0) existing.setUserID(newBooking.getUserID());
        if (newBooking.getRoomId() != 0) existing.setRoomId(newBooking.getRoomId());
        if (newBooking.getCheckInDate() != null) existing.setCheckInDate(newBooking.getCheckInDate());
        if (newBooking.getCheckOutDate() != null) existing.setCheckOutDate(newBooking.getCheckOutDate());
        if (newBooking.getStatus() != null) existing.setStatus(newBooking.getStatus());
        if (newBooking.getPaymentID() != 0) existing.setPaymentID(newBooking.getPaymentID());

        return bookingRepository.save(existing);
    }
    
    public List<Booking> getConflictingBookings(int roomId, Date checkIn, Date checkOut) {
        return bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
    }

    public List<Booking> getPreviousBookingsByUserID(int userId) {
        return bookingRepository.findPreviousBookingsByUserID(userId);
    }

}
