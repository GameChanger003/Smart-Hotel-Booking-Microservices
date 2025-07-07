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
        ResponseEntity<Loyality> loyaltyRes = loyalityClient.getLoyalityById(token, redemption.getUserID());

        if (!loyaltyRes.getStatusCode().is2xxSuccessful() || loyaltyRes.getBody() == null) {
            throw new IllegalArgumentException("Cannot record redemption: Loyalty account not found.");
        }

        return redemptionRepo.save(redemption);
    }


    public List<Redemption> getRedemption() {
        return redemptionRepo.findAll();
    }
}
