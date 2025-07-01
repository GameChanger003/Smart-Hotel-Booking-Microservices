package com.cts.hotel.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.hotel.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	Optional<Booking> findById(Integer id);

	@Query("SELECT b FROM Booking b WHERE b.RoomId = :roomId AND b.Status = 'BOOKED' AND "
		     + "(:checkInDate <= b.CheckOutDate AND :checkOutDate >= b.CheckInDate)")
		List<Booking> findConflictingBookings(@Param("roomId") int roomId,
		                                      @Param("checkInDate") Date checkIn,
		                                      @Param("checkOutDate") Date checkOut);

		@Query("SELECT b FROM Booking b WHERE b.CheckOutDate < CURRENT_DATE AND b.UserID = :userId")
		List<Booking> findPreviousBookingsByUserID(@Param("userId") int userId);
}
