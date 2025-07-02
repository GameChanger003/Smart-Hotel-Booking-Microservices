package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLoyaltyByUserId(@PathVariable("userId") int userId) {
        return accountService.fetchLoyaltyByUserId(userId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoyalty(
        @PathVariable("id") int loyaltyId,
        @RequestBody Loyality updatedLoyalty) {
        return accountService.updateLoyalty(loyaltyId, updatedLoyalty);
    }
}
