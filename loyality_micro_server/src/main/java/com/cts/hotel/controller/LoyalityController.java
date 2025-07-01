package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.hotel.model.Loyality;
import com.cts.hotel.service.LoyaltyAccountService;

@RestController
@RequestMapping("/user/loyality")
@CrossOrigin(origins = "*")
public class LoyalityController {

    @Autowired
    private LoyaltyAccountService accountService;

    @GetMapping
    public ResponseEntity<List<Loyality>> getAllLoyaltyPoints() {
        return accountService.fetchAllLoyalty();
    }

    @PostMapping("/add")
    public ResponseEntity<Loyality> addLoyalty(@RequestBody Loyality loyality) {
        return accountService.saveLoyalty(loyality);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLoyalty(@PathVariable int id) {
        return accountService.deleteLoyaltyById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoyaltyByUserId(@PathVariable int id) {
        return accountService.fetchLoyaltyByUserId(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoyalty(
        @PathVariable("id") int loyaltyId,
        @RequestBody Loyality updatedLoyalty
    ) {
        return accountService.updateLoyalty(loyaltyId, updatedLoyalty);
    }
}
