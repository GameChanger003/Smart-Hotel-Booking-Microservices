package com.cts.hotel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.hotel.model.Loyality;

@Repository
public interface LoyalityRepo extends JpaRepository<Loyality, Integer> {
    Loyality findByLoyaltyID(int loyaltyID);
    Loyality findByUserID(int userID);
}

