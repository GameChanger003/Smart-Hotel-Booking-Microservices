package com.cts.hotel.client;

import com.cts.hotel.model.Booking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "booking-microservice")
public interface PaymentServiceClient {

    @GetMapping("/users/booking/{bookingId}")
    ResponseEntity<Booking> getBookingById(
        @RequestHeader("Authorization") String token,
        @PathVariable("bookingId") int bookingId
    );

    @PutMapping(value = "/users/booking/updateBooking", consumes = "application/json")
    ResponseEntity<Booking> updateBooking(
        @RequestHeader("Authorization") String token,
        @RequestParam("oldBookingId") int oldBookingId,
        @RequestBody Booking updatedBooking
    );

}
