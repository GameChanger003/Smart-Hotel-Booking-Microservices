package com.cts.hotel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
