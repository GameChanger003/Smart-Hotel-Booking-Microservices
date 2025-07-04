package com.cts.hotel.hotelusers.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

//• Booking (BookingID, UserID, RoomID, CheckInDate, CheckOutDate, Status, PaymentID)

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int BookingID;
	@Column
	private int UserID;
	@Column
	private int RoomId;
	@Column(nullable = false)
	private Date CheckInDate;
	@Column(nullable = false)
	private Date CheckOutDate;
	@Column
	@Enumerated(EnumType.STRING)
	private BookingStatus Status;
	@Column
	private int PaymentID;

	public int getBookingID() {
		return BookingID;
	}

	public void setBookingID(int bookingID) {
		BookingID = bookingID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getRoomId() {
		return RoomId;
	}

	public void setRoomId(int roomId) {
		RoomId = roomId;
	}

	public Date getCheckInDate() {
		return CheckInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		CheckInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return CheckOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		CheckOutDate = checkOutDate;
	}

	public BookingStatus getStatus() {
		return Status;
	}

	public void setStatus(BookingStatus status) {
		Status = status;
	}

	public int getPaymentID() {
		return PaymentID;
	}

	public void setPaymentID(int paymentID) {
		PaymentID = paymentID;
	}

	public Booking(int bookingID, int userID, int roomId, Date checkInDate, Date checkOutDate, BookingStatus status,
			int paymentID) {
		super();
		BookingID = bookingID;
		UserID = userID;
		RoomId = roomId;
		CheckInDate = checkInDate;
		CheckOutDate = checkOutDate;
		this.Status = (status == null) ? BookingStatus.PENDING : status;

		PaymentID = paymentID;
	}

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Booking [BookingID=" + BookingID + ", UserID=" + UserID + ", RoomId=" + RoomId + ", CheckInDate="
				+ CheckInDate + ", CheckOutDate=" + CheckOutDate + ", Status=" + Status + ", PaymentID=" + PaymentID
				+ "]";
	}

	

}
