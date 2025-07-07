package com.cts.hotel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingTestController {
    @GetMapping("/ping")
    public String ping() {
        System.out.println("🔔 Ping received");
        return "pong";
    }
}
