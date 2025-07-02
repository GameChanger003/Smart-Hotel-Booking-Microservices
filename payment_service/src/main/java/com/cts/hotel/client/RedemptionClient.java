package com.cts.hotel.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.hotel.model.Redemption;

@FeignClient(name = "redemption-microservice")
public interface RedemptionClient {

	@PostMapping("/user/redemption/add")
	ResponseEntity<Redemption> saveRedemption(
	    @RequestHeader("Authorization") String token,
	    @RequestBody Redemption redemption
	);

}
