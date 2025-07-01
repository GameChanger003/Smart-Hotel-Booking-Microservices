package com.cts.hotel.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	Optional<Payment> findByBookingId(int bookingId);
	

}
