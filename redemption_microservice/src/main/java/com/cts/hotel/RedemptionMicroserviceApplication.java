package com.cts.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cts.hotel.client")
public class RedemptionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedemptionMicroserviceApplication.class, args);
	}

}
