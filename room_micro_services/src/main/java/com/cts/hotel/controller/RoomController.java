package com.cts.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.Room;
import com.cts.hotel.service.RoomService;

@RestController
@RequestMapping("/Manager/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@GetMapping
	public List<Room> getAllRooms() {
		return roomService.getAllRooms();
	}

	@GetMapping("/{id}")
	public Room getRoomById(@PathVariable int id) {
		System.out.println(id);
		return roomService.getRoomById(id);
	}

	@PostMapping("/add")
	public Room addRoom(@RequestBody Room room) {
		return roomService.addRoom(room);
	}

	@PutMapping("/update/{oldRoomID}")
	public Room updateRoom(@PathVariable int oldRoomID, @RequestBody Room newRoom) {
		return roomService.updateRoom(oldRoomID, newRoom);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteRoom(@PathVariable int id) {
		roomService.deleteRoom(id);
	}
}
