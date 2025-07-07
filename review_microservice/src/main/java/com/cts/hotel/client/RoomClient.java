package com.cts.hotel.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.hotel.model.Room;

@Service
@FeignClient(name = "RoomMicroServices")
@RequestMapping("/Manager/rooms")
public interface RoomClient {

    @GetMapping
    List<Room> getAllRooms(@RequestHeader("Authorization") String token);

    @GetMapping("/{roomId}")
    Room getRoomById(
        @RequestHeader("Authorization") String token,
        @PathVariable("roomId") int roomId
    );

    @PostMapping("/add")
    Room addRoom(
        @RequestHeader("Authorization") String token,
        @RequestBody Room room
    );

    @PutMapping("/update/{oldRoomID}")
    Room updateRoom(
        @RequestHeader("Authorization") String token,
        @PathVariable("oldRoomID") int oldRoomID,
        @RequestBody Room newRoom
    );

    @DeleteMapping("/delete/{id}")
    void deleteRoom(
        @RequestHeader("Authorization") String token,
        @PathVariable("id") int id
    );
}
