package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.Hotel;
import com.cts.hotel.service.HotelService;
@RestController
@RequestMapping("/Manager/hotels")
@CrossOrigin(origins = "*")
public class HotelController {
	
	

	@Autowired
	private HotelService hotelService;

	@GetMapping
	public List<Hotel> getAllHotels() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    System.out.println("👤 User: " + auth.getName());
		    System.out.println("🎭 Authorities: " + auth.getAuthorities());
		    return hotelService.getAllHotels();
		}

	@GetMapping("/{id}")
	public Hotel getHotelById(@PathVariable int id) {
		return hotelService.getHotelById(id);
	}

	@PostMapping("/add")
	public Hotel addHotel(@RequestBody Hotel hotel) {
		System.out.println(hotel);
		return hotelService.addHotel(hotel);
	}

	@PutMapping("/update/{oldHotelID}")
	public Hotel updateHotel(@PathVariable int oldHotelID, @RequestBody Hotel newHotel) {
		return hotelService.updateHotel(oldHotelID, newHotel);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteHotel(@PathVariable int id) {
		hotelService.deleteHotel(id);
	}
}
