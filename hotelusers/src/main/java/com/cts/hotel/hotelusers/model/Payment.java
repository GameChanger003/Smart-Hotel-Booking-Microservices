package com.cts.hotel.hotelusers.model;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//• Payment (PaymentID, UserID, BookingID, Amount, Status, PaymentMethod)

@Entity
@Table(name = "Payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId;
	
	@Column(nullable = false)
	private int userId;
	
	@Column(nullable = false)
	private int bookingId;
	
	@Column(nullable = false)
	private int amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentType paymentMethod ;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public PaymentType getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Payment(int paymentId, int userId, int bookingId, int amount, PaymentStatus status,
			PaymentType paymentMethod) {
		super();
		this.paymentId = paymentId;
		this.userId = userId;
		this.bookingId = bookingId;
		this.amount = amount;
		this.status = status;
		this.paymentMethod = paymentMethod;
	}

	public Payment() {
		super();
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", userId=" + userId + ", bookingId=" + bookingId + ", amount="
				+ amount + ", status=" + status + ", paymentMethod=" + paymentMethod + "]";
	}

	
	
	
}
