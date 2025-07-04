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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.Payment;
import com.cts.hotel.service.PaymentService;

@RestController
@RequestMapping("users/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/booking/{bookingId}/id")
	public ResponseEntity<Integer> getPaymentIdByBooking(@PathVariable int bookingId) {
		int paymentId = paymentService.getPaymentIdByBookingId(bookingId);
		return ResponseEntity.ok(paymentId);
	}

	@PostMapping
	public ResponseEntity<String> savePayment(@RequestHeader("Authorization") String token,
			@RequestBody Payment payment) {
		return paymentService.savePayment(payment, token);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable int paymentId) {
		return ResponseEntity.ok(paymentService.fetchPaymentById(paymentId));
	}

	@DeleteMapping("/{paymentId}")
	public ResponseEntity<Payment> deletePayment(@PathVariable int paymentId) {
		return ResponseEntity.ok(paymentService.deletePaymentById(paymentId));
	}

	@PutMapping("/{oldPaymentId}")
	public ResponseEntity<Payment> updatePayment(@PathVariable int oldPaymentId, @RequestBody Payment updatedPayment) {
		return ResponseEntity.ok(paymentService.updatePaymentById(oldPaymentId, updatedPayment));
	}

	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayments() {
		return ResponseEntity.ok(paymentService.fetchAllPayment());
	}
}
