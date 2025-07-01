package com.cts.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.client.PaymentServiceClient;
import com.cts.hotel.dao.PaymentDao;
import com.cts.hotel.model.Booking;
import com.cts.hotel.model.Payment;
import com.cts.hotel.model.PaymentStatus;
import com.cts.hotel.repo.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentServiceClient bookingClient;

    /**
     * Saves payment and updates corresponding booking with payment ID if successful.
     *
     * @param payment the payment data submitted by the user
     * @param token   the bearer token used to authenticate with the booking microservice
     * @return response indicating outcome of the operation
     */
    public ResponseEntity<String> savePayment(Payment payment, String token) {
        int bookingId = payment.getBookingId();

        // 1. Retrieve full booking object using Feign
        ResponseEntity<Booking> response = bookingClient.getBookingById(token, bookingId);
        Booking booking = response.getBody();

        if (booking == null || response.getStatusCode().is4xxClientError()) {
            return ResponseEntity.badRequest().body("Invalid booking ID: " + bookingId);
        }

        // 2. Handle unsuccessful payment status
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            return ResponseEntity.badRequest()
                .body("Payment failed. Status: " + payment.getStatus());
        }

        // 3. Save payment in database
        Payment savedPayment = paymentDao.savePayment(payment);

        // 4. Set payment ID in booking and update it via Feign client
        booking.setPaymentID(savedPayment.getPaymentId());
        ResponseEntity<Booking> updatedBooking = bookingClient.updateBooking(token, bookingId, booking);

        if (updatedBooking.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Payment saved and booking updated successfully.");
        } else {
            return ResponseEntity.internalServerError().body("Payment saved, but failed to update booking.");
        }
    }

    /**
     * Retrieves the payment ID associated with a booking ID.
     */
    public int getPaymentIdByBookingId(int bookingId) {
        return paymentRepository.findByBookingId(bookingId)
            .map(Payment::getPaymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found for Booking ID: " + bookingId));
    }

    public Payment fetchPaymentById(int paymentId) {
        return paymentDao.fetchPaymentById(paymentId);
    }

    public Payment deletePaymentById(int paymentId) {
        return paymentDao.deletePaymentById(paymentId);
    }

    public Payment updatePaymentById(int oldPaymentId, Payment updatedPayment) {
        return paymentDao.updatePaymentById(oldPaymentId, updatedPayment);
    }

    public List<Payment> fetchAllPayment() {
        return paymentDao.fetchAllPayment();
    }
}
