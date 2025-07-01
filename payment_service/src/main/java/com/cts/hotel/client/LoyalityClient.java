package com.cts.hotel.client;

import com.cts.hotel.model.Loyality;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "loyality-micro-server")
public interface LoyalityClient {

    @GetMapping("/user/loyality/{id}")
    ResponseEntity<Loyality> getLoyalityById(
        @RequestHeader("Authorization") String token,
        @PathVariable("id") int id // ✅ fixed mismatch from "bookingId" to "id"
    );

    @PostMapping(value = "/user/loyality/add", consumes = "application/json")
    ResponseEntity<Loyality> saveLoyalty(
        @RequestHeader("Authorization") String token,
        @RequestBody Loyality loyality
    );

    @PutMapping(value = "/user/loyality/update/{id}", consumes = "application/json")
    ResponseEntity<Loyality> updateLoyalty(
        @RequestHeader("Authorization") String token,
        @PathVariable("id") int loyaltyId,
        @RequestBody Loyality updatedLoyalty
    );
}
