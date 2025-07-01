package com.cts.hotel.hotelusers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//• Redemption (RedemptionID, UserID, BookingID, PointsUsed, DiscountAmount)

@Entity
@Table
public class Redemption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int RedemptionID;
	private int UserID;
	private int BookingID;
	private int PointsUsed;
	private float DiscountAmount;

	public int getRedemptionID() {
		return RedemptionID;
	}

	public void setRedemptionID(int redemptionID) {
		RedemptionID = redemptionID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getBookingID() {
		return BookingID;
	}

	public void setBookingID(int bookingID) {
		BookingID = bookingID;
	}

	public int getPointsUsed() {
		return PointsUsed;
	}

	public void setPointsUsed(int pointsUsed) {
		PointsUsed = pointsUsed;
	}

	public float getDiscountAmount() {
		return DiscountAmount;
	}

	public void setDiscountAmount(float discountAmount) {
		DiscountAmount = discountAmount;
	}

	public Redemption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Redemption(int redemptionID, int userID, int bookingID, int pointsUsed, float discountAmount) {
		super();
		RedemptionID = redemptionID;
		UserID = userID;
		BookingID = bookingID;
		PointsUsed = pointsUsed;
		DiscountAmount = discountAmount;
	}
}
