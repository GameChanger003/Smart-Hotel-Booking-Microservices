package com.cts.hotel.service;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.client.BookingClient;
import com.cts.hotel.client.RoomClient;
import com.cts.hotel.dao.ReviewDao;
import com.cts.hotel.model.Booking;
import com.cts.hotel.model.Review;
import com.cts.hotel.model.Room;

@Service
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private RoomClient roomClient;

    public ResponseEntity<?> saveReview(String token, Review review, int bookingId) {

        ResponseEntity<Booking> bookingResponse = bookingClient.getBookingById(token, bookingId);
        if (!bookingResponse.getStatusCode().is2xxSuccessful() || bookingResponse.getBody() == null) {
            return ResponseEntity.status(404).body("Booking not found.");
        }

        Booking booking = bookingResponse.getBody();
        if (booking.getCheckOutDate() == null) {
            return ResponseEntity.badRequest().body("Booking has no checkout date.");
        }

        LocalDate checkout = booking.getCheckOutDate().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate today = LocalDate.now();
        if (!checkout.isBefore(today) && !checkout.isEqual(today)) {
            return ResponseEntity.status(403).body("Cannot review before checkout is complete.");
        }

        Room room = roomClient.getRoomById(token, booking.getRoomId());
        if (room == null) {
            return ResponseEntity.status(404).body("Room not found.");
        }

        // Populate from client input
        review.setUser(booking.getUserID());
        review.setHotelId(room.getHotelID());
        review.setTimeStamp(booking.getCheckOutDate());

        return ResponseEntity.ok(reviewDao.saveReview(review));
    }
}
