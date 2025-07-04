package com.cts.hotel.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name ="loyaltyAccount")
public class LoyaltyAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loyaltyId;
	@Column(nullable = false)
	private int userId;
	@Column(nullable = false)
	private int pointsBalance;
	@Column(nullable = false)
	private Date lastUpdated;
	
	
	public int getLoyaltyId() {
		return loyaltyId;
	}
	public void setLoyaltyId(int loyaltyId) {
		this.loyaltyId = loyaltyId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPointsBalance() {
		return pointsBalance;
	}
	public void setPointsBalance(int pointsBalance) {
		this.pointsBalance = pointsBalance;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public LoyaltyAccount(int loyaltyId, int userId, int pointsBalance, Date lastUpdated) {
		super();
		this.loyaltyId = loyaltyId;
		this.userId = userId;
		this.pointsBalance = pointsBalance;
		this.lastUpdated = lastUpdated;
	}
	public LoyaltyAccount() {
		super();
	}
	@Override
	public String toString() {
		return "LoyaltyAccount [loyaltyId=" + loyaltyId + ", userId=" + userId + ", pointsBalance=" + pointsBalance
				+ ", lastUpdated=" + lastUpdated + "]";
	}
	
	
	
	

}
