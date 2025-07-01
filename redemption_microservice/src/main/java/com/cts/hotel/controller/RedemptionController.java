package com.cts.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.hotel.model.Redemption;
import com.cts.hotel.service.RedemptionService;

@RestController
@RequestMapping("/user/redemption")
@CrossOrigin(origins = "*")
public class RedemptionController {

    @Autowired
    private RedemptionService redemptionService;

    @GetMapping
    public ResponseEntity<?> getAllRedemptions() {
        return ResponseEntity.ok(redemptionService.getRedemption());
    }

    @PostMapping("/add")
    public ResponseEntity<Redemption> saveRedemption(
        @RequestBody Redemption redemption,
        @RequestHeader("Authorization") String token
    ) {
        Redemption saved = redemptionService.saveRedemption(redemption, token);
        return ResponseEntity.ok(saved);
    }
}
