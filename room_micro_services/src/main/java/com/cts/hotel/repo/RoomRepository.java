package com.cts.hotel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.hotel.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
	
    Room findByroomID(int roomId);
}
