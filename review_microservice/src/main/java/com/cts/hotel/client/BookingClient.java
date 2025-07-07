package com.cts.hotel.client;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.hotel.model.Booking;

@Service
@FeignClient(name = "booking-microservice")
public interface BookingClient {

    @PostMapping("/users/booking/book")
    ResponseEntity<?> bookRoomIfAvailable(
        @RequestHeader("Authorization") String token,
        @RequestBody Booking booking
    );

    @GetMapping("/users/booking")
    List<Booking> getAllBookings(
        @RequestHeader("Authorization") String token
    );

    @GetMapping("/users/booking/{bookingId}")
    ResponseEntity<Booking> getBookingById(
        @RequestHeader("Authorization") String token,
        @PathVariable("bookingId") int bookingId
    );

    @DeleteMapping("/users/booking/cancel/{bookingId}")
    ResponseEntity<?> cancelBooking(
        @RequestHeader("Authorization") String token,
        @PathVariable("bookingId") int bookingId
    );

    @PutMapping("/users/booking/updateBooking")
    ResponseEntity<Booking> updateBooking(
        @RequestHeader("Authorization") String token,
        @RequestParam("oldBookingId") int oldBookingId,
        @RequestBody Booking updatedBooking
    );

    @GetMapping("/users/booking/user/{userId}/past")
    List<Booking> getPreviousBookingsByUser(
        @RequestHeader("Authorization") String token,
        @PathVariable("userId") int userId
    );

    @GetMapping("/users/booking/conflicts")
    List<Booking> getConflictingBookings(
        @RequestHeader("Authorization") String token,
        @RequestParam("roomId") int roomId,
        @RequestParam("checkInDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) Date checkInDate,
        @RequestParam("checkOutDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) Date checkOutDate
    );
}
