package com.cts.hotel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.hotel.model.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}
