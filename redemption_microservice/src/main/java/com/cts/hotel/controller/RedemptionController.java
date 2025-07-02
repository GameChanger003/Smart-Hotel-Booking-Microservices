package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.Redemption;
import com.cts.hotel.service.RedemptionService;

@RestController
@RequestMapping("/user/redemption")
@CrossOrigin(origins = "*")
public class RedemptionController {

	@Autowired
	private RedemptionService redemptionService;

	@GetMapping
	public ResponseEntity<List<Redemption>> getAllRedemptions() {
		List<Redemption> redemptions = redemptionService.getRedemption();
		return ResponseEntity.ok(redemptions);
	}

	@PostMapping("/add")
	public ResponseEntity<Redemption> saveRedemption(
	    @RequestBody Redemption redemption,
	    @RequestHeader("Authorization") String token
	){
		Redemption saved = redemptionService.saveRedemption(redemption, token);
		return ResponseEntity.ok(saved);
	}
}
