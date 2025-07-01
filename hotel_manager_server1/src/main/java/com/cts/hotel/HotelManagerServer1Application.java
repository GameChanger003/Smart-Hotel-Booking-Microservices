package com.cts.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelManagerServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(HotelManagerServer1Application.class, args);
	}

}
