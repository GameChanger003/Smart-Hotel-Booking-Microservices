package com.cts.hotel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.hotel.client.LoyalityClient;
import com.cts.hotel.model.Loyality;
import com.cts.hotel.model.Redemption;
import com.cts.hotel.repo.RedemptionRepo;

@Service
public class RedemptionService {

    @Autowired
    private RedemptionRepo redemptionRepo;

    @Autowired
    private LoyalityClient loyalityClient;

    public Redemption saveRedemption(Redemption redemption, String token) {
        // 1. Fetch loyalty data via Feign
        ResponseEntity<Loyality> loyaltyRes = loyalityClient.getLoyalityById(token, redemption.getUserID());
        Loyality loyalty = loyaltyRes.getBody();

        if (loyalty == null || loyaltyRes.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException("User does not have a valid loyalty account.");
        }

        // 2. Deduct points
        int updatedPoints = loyalty.getPointsBalance() - redemption.getPointsUsed();
        if (updatedPoints < 0) {
            throw new IllegalArgumentException("Insufficient loyalty points.");
        }

        loyalty.setPointsBalance(updatedPoints);
        loyalty.setLastUpdated(new Date());

        // 3. Update via Feign
        loyalityClient.updateLoyalty(token, loyalty.getLoyaltyID(), loyalty);

        // 4. Save redemption record
        return redemptionRepo.save(redemption);
    }

    public List<Redemption> getRedemption() {
        return redemptionRepo.findAll();
    }
}
