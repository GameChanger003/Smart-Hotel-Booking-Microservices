package com.cts.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.hotel.model.Review;
import com.cts.hotel.repo.ReviewRepository;



@Repository
public class ReviewDao {
	
	@Autowired
    ReviewRepository reviewRepository;
	
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}
	
	public Review fetchReviewById(int reviewId) {
		return reviewRepository.findById(reviewId).get();
	}
	
	public Review deleteReviewById(int reviewId) {
		Review review = fetchReviewById(reviewId);
		reviewRepository.delete(review);
		return review;
	}
	
	public Review updateReview(int oldReviewId, Review newReview) {
		newReview.setReviewId(oldReviewId);
		return saveReview(newReview);
	}
    
	public List<Review> fetchAllReview(){
		
		return reviewRepository.findAll();
	}

}
