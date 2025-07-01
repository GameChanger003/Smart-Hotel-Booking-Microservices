package com.cts.hotel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.client.BookingClient;
import com.cts.hotel.client.LoyalityClient;
import com.cts.hotel.dao.PaymentDao;
import com.cts.hotel.model.Booking;
import com.cts.hotel.model.Loyality;
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
    private BookingClient bookingClient;

    @Autowired
    private LoyalityClient loyalityClient;

    public ResponseEntity<String> savePayment(Payment payment, String token) {
        int bookingId = payment.getBookingId();

        ResponseEntity<Booking> response = bookingClient.getBookingById(token, bookingId);
        Booking booking = response.getBody();

        if (booking == null || response.getStatusCode().is4xxClientError()) {
            return ResponseEntity.badRequest().body("Invalid booking ID: " + bookingId);
        }

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            return ResponseEntity.badRequest().body("Payment failed. Status: " + payment.getStatus());
        }

        ResponseEntity<Loyality> loyaltyRes = loyalityClient.getLoyalityById(token, payment.getUserId());
        Loyality loyalty = loyaltyRes.getBody();
        int originalAmount = payment.getAmount();
        int finalAmount = originalAmount;

        if (payment.isUseLoyaltyPoints() && loyalty != null) {
            int availablePoints = loyalty.getPointsBalance();
            int redeemable = Math.min(availablePoints, originalAmount);
            finalAmount = originalAmount - redeemable;

            loyalty.setPointsBalance(availablePoints - redeemable);
            loyalty.setLastUpdated(new Date());
            loyalityClient.updateLoyalty(token, loyalty.getLoyaltyID(), loyalty);
        }

        payment.setAmount(finalAmount);
        Payment savedPayment = paymentDao.savePayment(payment);

        int earnedPoints = finalAmount / 5;

        if (loyalty == null || loyaltyRes.getStatusCode().is4xxClientError()) {
            Loyality newLoyalty = new Loyality();
            newLoyalty.setUserID(payment.getUserId());
            newLoyalty.setPointsBalance(earnedPoints);
            newLoyalty.setLastUpdated(new Date());
            loyalityClient.saveLoyalty(token, newLoyalty);
        } else {
            loyalty.setPointsBalance(loyalty.getPointsBalance() + earnedPoints);
            loyalty.setLastUpdated(new Date());
            loyalityClient.updateLoyalty(token, loyalty.getLoyaltyID(), loyalty);
        }

        booking.setPaymentID(savedPayment.getPaymentId());
        ResponseEntity<Booking> updatedBooking = bookingClient.updateBooking(token, bookingId, booking);

        if (updatedBooking.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Payment saved, booking updated, and loyalty points processed.");
        } else {
            return ResponseEntity.internalServerError().body("Payment saved, but booking update failed.");
        }
    }


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
