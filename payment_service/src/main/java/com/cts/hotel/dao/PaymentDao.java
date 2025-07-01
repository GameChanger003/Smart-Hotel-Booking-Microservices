package com.cts.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.hotel.model.Payment;
import com.cts.hotel.repo.PaymentRepository;


@Repository
public class PaymentDao {

	
	@Autowired
	PaymentRepository paymentRepository;

	public Payment savePayment(Payment payment) {

		return paymentRepository.save(payment);

	}

	public Payment fetchPaymentById(int paymentId) {

		return paymentRepository.findById(paymentId).get();
	}

	public Payment deletePaymentById(int paymentId) {

		Payment payment = fetchPaymentById(paymentId);

		paymentRepository.delete(payment);

		return payment;
	}

	public Payment updatePaymentById(int oldpaymentId, Payment newPayment) {

		newPayment.setPaymentId(oldpaymentId);
		return savePayment(newPayment);

	}

	public List<Payment> fetchAllPayment() {
		return paymentRepository.findAll();
	}

}
