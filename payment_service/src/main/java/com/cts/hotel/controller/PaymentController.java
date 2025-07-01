package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.hotel.model.Payment;
import com.cts.hotel.service.PaymentService;

@RestController
@RequestMapping("/users/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Fetch payment ID by booking ID.
     */
    @GetMapping("/id")
    public ResponseEntity<Integer> getPaymentIdByBooking(@RequestParam("bookingId") int bookingId) {
        int paymentId = paymentService.getPaymentIdByBookingId(bookingId);
        return ResponseEntity.ok(paymentId);
    }

    /**
     * Save a new payment and update booking.
     */
    @PostMapping
    public ResponseEntity<String> savePayment(
            @RequestHeader("Authorization") String token,
            @RequestBody Payment payment) {
        return paymentService.savePayment(payment, token);
    }

    /**
     * Fetch payment details by payment ID.
     */
    @GetMapping("/fetch")
    public ResponseEntity<Payment> getPaymentById(@RequestParam("paymentId") int paymentId) {
        return ResponseEntity.ok(paymentService.fetchPaymentById(paymentId));
    }

    /**
     * Delete payment by ID.
     */
    @DeleteMapping
    public ResponseEntity<Payment> deletePayment(@RequestParam("paymentId") int paymentId) {
        return ResponseEntity.ok(paymentService.deletePaymentById(paymentId));
    }

    /**
     * Update an existing payment.
     */
    @PutMapping
    public ResponseEntity<Payment> updatePayment(
            @RequestParam("oldPaymentId") int oldPaymentId,
            @RequestBody Payment updatedPayment) {
        return ResponseEntity.ok(paymentService.updatePaymentById(oldPaymentId, updatedPayment));
    }

    /**
     * Fetch all payments.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.fetchAllPayment());
    }
}
