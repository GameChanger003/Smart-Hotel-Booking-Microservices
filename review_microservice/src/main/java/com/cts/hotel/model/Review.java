package com.cts.hotel.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;

//	@OneToOne
	// @JoinColumn(name="UserId")
	// @Column(name = "UserId")
	private int UserID;
	private int hotelId;
	private float rating;
	private String comment;
	@Transient
	private int bookingId;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	// @Value("#{new java.util.Date()}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp = new Date();

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date date) {
		this.timeStamp = date;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getUser() {
		return UserID;
	}

	public void setUser(int user) {
		this.UserID = user;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Review(int reviewId, int user, int hotelId, float rating, String comment, Date timeStamp) {
		super();
		this.reviewId = reviewId;
		this.UserID = user;
		this.hotelId = hotelId;
		this.rating = rating;
		this.comment = comment;
		this.timeStamp = timeStamp;
	}

	public Review() {
		super();
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", user=" + UserID + ", hotelId=" + hotelId + ", rating=" + rating
				+ ", comment=" + comment + ", timeStamp=" + timeStamp + "]";
	}

}
