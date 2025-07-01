package com.cts.hotel.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//• LoyaltyAccount (LoyaltyID, UserID, PointsBalance, LastUpdated)

@Entity
@Table(name = "Loy")
public class Loyality {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loyaltyID;
	@Column
	private int userID;
	@Column
	private int PointsBalance;
	@Column
	private Date LastUpdated;

	public int getLoyaltyID() {
		return loyaltyID;
	}

	public void setLoyaltyID(int id) {
		loyaltyID = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int id) {
		userID = id;
	}

	public int getPointsBalance() {
		return PointsBalance;
	}

	public void setPointsBalance(int pointsBalance) {
		PointsBalance = pointsBalance;
	}

	public Date getLastUpdated() {
		return LastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		LastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Loyality [LoyaltyID=" + loyaltyID + ", UserID=" + userID + ", PointsBalance=" + PointsBalance
				+ ", LastUpdated=" + LastUpdated + "]";
	}

	public Loyality(int loyID, int userId, int pointsBalance, Date lastUpdated) {
		super();
		loyaltyID = loyID;
		userID = userId;
		PointsBalance = pointsBalance;
		LastUpdated = lastUpdated;
	}

	public Loyality() {
		super();
		// TODO Auto-generated constructor stub
	}

}
