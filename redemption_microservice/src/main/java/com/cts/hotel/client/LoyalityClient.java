package com.cts.hotel.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.hotel.model.Loyality;

@Service
@FeignClient(name = "loyality-micro-server")
public interface LoyalityClient {

	@GetMapping("/user/loyality/user/{userId}")
	ResponseEntity<Loyality> getLoyalityById(
	    @RequestHeader("Authorization") String token,
	    @PathVariable("userId") int userId
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
