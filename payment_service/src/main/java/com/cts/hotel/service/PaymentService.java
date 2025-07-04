package com.cts.hotel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.client.BookingClient;
import com.cts.hotel.client.LoyalityClient;
import com.cts.hotel.client.RedemptionClient;
import com.cts.hotel.dao.PaymentDao;
import com.cts.hotel.model.Booking;
import com.cts.hotel.model.Loyality;
import com.cts.hotel.model.Payment;
import com.cts.hotel.model.PaymentStatus;
import com.cts.hotel.model.Redemption;
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

    @Autowired
    private RedemptionClient redemptionClient;

    public ResponseEntity<String> savePayment(Payment payment, String token) {
        int bookingId = payment.getBookingId();

        ResponseEntity<Booking> bookingResponse = bookingClient.getBookingById(token, bookingId);
        if (bookingResponse.getStatusCode().is4xxClientError() || bookingResponse.getBody() == null) {
            return ResponseEntity.badRequest().body("Invalid booking ID: " + bookingId);
        }

        Booking booking = bookingResponse.getBody();

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            return ResponseEntity.badRequest().body("Payment failed. Status: " + payment.getStatus());
        }

        Loyality loyalty = null;
        boolean isNewUser = false;

        try {
            ResponseEntity<Loyality> loyaltyResp = loyalityClient.getLoyaltyByUserId(token, payment.getUserId());
            if (loyaltyResp.getStatusCode().is2xxSuccessful()) {
                loyalty = loyaltyResp.getBody();
            }
        } catch (Exception e) {
            isNewUser = true;
        }

        int originalAmount = payment.getAmount();
        int finalAmount = originalAmount;

        if (payment.isUseLoyaltyPoints() && loyalty != null) {
            int availablePoints = loyalty.getPointsBalance();
            int redeemable = Math.min(availablePoints, originalAmount);
            finalAmount = originalAmount - redeemable;

            loyalty.setPointsBalance(availablePoints - redeemable);
            loyalty.setLastUpdated(new Date());
            loyalityClient.updateLoyalty(token, loyalty.getLoyaltyID(), loyalty);

            Redemption redemption = new Redemption();
            redemption.setUserID(payment.getUserId());
            redemption.setBookingID(bookingId);
            redemption.setPointsUsed(redeemable);
            redemption.setDiscountAmount(redeemable);
            redemptionClient.saveRedemption(token, redemption);
        }

        payment.setAmount(finalAmount);
        Payment savedPayment = paymentDao.savePayment(payment);

        int earnedPoints = finalAmount / 5;

        if (isNewUser || loyalty == null) {
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

        Redemption reward = new Redemption();
        reward.setUserID(payment.getUserId());
        reward.setBookingID(bookingId);
        reward.setPointsUsed(-earnedPoints);
        reward.setDiscountAmount(0);
        redemptionClient.saveRedemption(token, reward);

        booking.setPaymentID(savedPayment.getPaymentId());
        ResponseEntity<Booking> bookingUpdate = bookingClient.updateBooking(token, bookingId, booking);

        if (bookingUpdate.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Payment processed, loyalty updated, booking confirmed.");
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
