package com.cts.hotel.DAO;

import java.util.List;

import com.cts.hotel.model.Room;

public interface RoomDAO {
	List<Room> getAllRooms();
	
	Room getRoomById(int id);
	
	Room addRoom(Room room);
	
	Room updateRoom(int oldRoomID, Room newRoom);
	
	void deleteRoom(int id);

}
