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

        // 1. Fetch booking details
        ResponseEntity<Booking> bookingResponse = bookingClient.getBookingById(token, bookingId);
        Booking booking = bookingResponse.getBody();

        if (booking == null || bookingResponse.getStatusCode().is4xxClientError()) {
            return ResponseEntity.badRequest().body("Invalid booking ID: " + bookingId);
        }

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            return ResponseEntity.badRequest().body("Payment failed. Status: " + payment.getStatus());
        }

        // 2. Fetch or initialize loyalty
        ResponseEntity<Loyality> loyaltyResponse = loyalityClient.getLoyalityById(token, payment.getUserId());
        Loyality loyalty = loyaltyResponse.getBody();

        int originalAmount = payment.getAmount();
        int finalAmount = originalAmount;

        // 3. Apply loyalty points if requested
        if (payment.isUseLoyaltyPoints() && loyalty != null) {
            int availablePoints = loyalty.getPointsBalance();
            int redeemable = Math.min(availablePoints, originalAmount);
            finalAmount = originalAmount - redeemable;

            // Update loyalty balance
            loyalty.setPointsBalance(availablePoints - redeemable);
            loyalty.setLastUpdated(new Date());
            loyalityClient.updateLoyalty(token, loyalty.getLoyaltyID(), loyalty);

            // Log redemption
            Redemption redemption = new Redemption();
            redemption.setUserID(payment.getUserId());
            redemption.setBookingID(bookingId);
            redemption.setPointsUsed(redeemable);
            redemption.setDiscountAmount(redeemable);
            redemptionClient.saveRedemption(token, redemption);
        }

        // 4. Save adjusted payment
        payment.setAmount(finalAmount);
        Payment savedPayment = paymentDao.savePayment(payment);

        // 5. Earn points based on final amount
        int earnedPoints = finalAmount / 5;

        if (loyalty == null || loyaltyResponse.getStatusCode().is4xxClientError()) {
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

        // 6. Log point earning transaction
        Redemption rewardLog = new Redemption();
        rewardLog.setUserID(payment.getUserId());
        rewardLog.setBookingID(bookingId);
        rewardLog.setPointsUsed(-earnedPoints); // Earned points as negative usage
        rewardLog.setDiscountAmount(0); // No discount when earning
        redemptionClient.saveRedemption(token, rewardLog);

        // 7. Link payment to booking
        booking.setPaymentID(savedPayment.getPaymentId());
        ResponseEntity<Booking> bookingUpdate = bookingClient.updateBooking(token, bookingId, booking);

        if (bookingUpdate.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Payment processed, points applied and logged, booking updated.");
        } else {
            return ResponseEntity.internalServerError().body("Payment saved, but failed to update booking.");
        }
    }

    // Other CRUD methods remain unchanged

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
