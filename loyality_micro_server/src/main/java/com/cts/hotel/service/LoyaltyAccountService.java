package com.cts.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.model.Loyality;
import com.cts.hotel.repo.LoyalityRepo;

@Service
public class LoyaltyAccountService {

    @Autowired
    private LoyalityRepo loyalityRepo;

    public ResponseEntity<Loyality> saveLoyalty(Loyality loyaltyAccount) {
        Loyality saved = loyalityRepo.save(loyaltyAccount);
        System.out.println(saved);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> fetchLoyaltyById(int loyaltyId) {
        Loyality loyality = loyalityRepo.findByLoyaltyID(loyaltyId);
        if (loyality != null) {
            return ResponseEntity.ok(loyality);
        }
        return ResponseEntity.status(404).body("Loyalty ID " + loyaltyId + " not found.");
    }

    public ResponseEntity<?> fetchLoyaltyByUserId(int userId) {
        Loyality loyality = loyalityRepo.findByUserID(userId);
        if (loyality != null) {
            return ResponseEntity.ok(loyality);
        }
        return ResponseEntity.status(404).body("User ID " + userId + " not found.");
    }

    public ResponseEntity<String> deleteLoyaltyById(int loyaltyId) {
        if (loyalityRepo.existsById(loyaltyId)) {
            loyalityRepo.deleteById(loyaltyId);
            return ResponseEntity.ok("Deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Loyalty ID not found.");
        }
    }

    public ResponseEntity<?> updateLoyalty(int oldLoyaltyId, Loyality newLoyalty) {
        if (!loyalityRepo.existsById(oldLoyaltyId)) {
            return ResponseEntity.status(404).body("Cannot update. Loyalty ID " + oldLoyaltyId + " not found.");
        }
        newLoyalty.setLoyaltyID(oldLoyaltyId);
        Loyality updated = loyalityRepo.save(newLoyalty);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<List<Loyality>> fetchAllLoyalty() {
        List<Loyality> list = loyalityRepo.findAll();
        return ResponseEntity.ok(list);
    }
}
