package com.cts.hotel.hotelserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
	    "com.cts.hotel.hotelserver",
	    "com.cts.hotel.security",
	    "com.cts.hotel.JWT",
	    "com.cts.hotel.controller",
	    "com.cts.hotel.service",
	    "com.cts.hotel.repo",
	    "com.cts.hotel.dao",
	    "com.cts.hotel.model",
	})

@EnableDiscoveryClient
public class HotelserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelserverApplication.class, args);
	}

}
