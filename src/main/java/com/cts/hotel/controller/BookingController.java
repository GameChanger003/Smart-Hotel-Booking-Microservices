package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.Booking;
import com.cts.hotel.service.BookingService;
import com.cts.hotel.service.BookingServiceImpl;

@RestController
@RequestMapping("/users/booking")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

//    @Autowired
//    private PaymentService paymentService;
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable int bookingId) {
        return bookingService.getById(bookingId); 
    }



    @PostMapping("/book")
    public ResponseEntity<Booking> bookRoom(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.bookRoom(booking));
    }


    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable int id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking Cancelled");
    }
    
    @PutMapping("/updateBooking")
    public ResponseEntity<Booking> updateBooking(
        @RequestParam("oldBookingId") int oldBookingId,
        @RequestBody Booking newBooking
    ) {
    	System.out.println(newBooking);
        Booking updated = bookingService.updateBooking(oldBookingId, newBooking);
        return ResponseEntity.ok(updated);
    }

}
