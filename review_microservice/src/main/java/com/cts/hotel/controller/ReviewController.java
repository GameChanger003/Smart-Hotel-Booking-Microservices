package com.cts.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.Review;
import com.cts.hotel.service.ReviewService;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/saveReview")
    public ResponseEntity<?> saveReview(
        @RequestHeader("Authorization") String token,
        @RequestBody Review review
    ) {
        int bookingId = review.getBookingId();
        return reviewService.saveReview(token, review, bookingId);
    }
}
