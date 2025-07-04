package com.cts.hotel.hotelusers.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/dashboard")
    public ResponseEntity<?> getUserDashboard() {
        return ResponseEntity.ok(Collections.singletonMap("message", "Welcome to User Dashboard!"));
    }
}

