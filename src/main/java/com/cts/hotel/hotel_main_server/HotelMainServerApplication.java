package com.cts.hotel.hotel_main_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HotelMainServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelMainServerApplication.class, args);
	}

}
