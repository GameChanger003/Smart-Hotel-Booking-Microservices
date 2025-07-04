package com.cts.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.hotel.DAO.RoomDAO;
import com.cts.hotel.model.Room;
import com.cts.hotel.repo.RoomRepository;

@Service
public class RoomService implements RoomDAO {

	@Autowired
	private RoomRepository roomRepository;

	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	public Room getRoomById(int id) {
		return roomRepository.findById(id).orElse(null);
	}

	public Room addRoom(Room room) {
		return roomRepository.save(room);
	}

	public Room updateRoom(int oldRoomID, Room newRoom) {
		newRoom.setRoomID(oldRoomID);
		return addRoom(newRoom);
	}

	public void deleteRoom(int id) {
		roomRepository.deleteById(id);
	}
}
