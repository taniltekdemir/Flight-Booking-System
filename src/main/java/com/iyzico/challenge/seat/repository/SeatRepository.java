package com.iyzico.challenge.seat.repository;

import com.iyzico.challenge.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    Seat findFirstByFlightIdAndSeatNumber(Long flightId, String seatNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id = :seatId AND s.status = 'AVAILABLE'")
    Optional<Seat> findAvailableSeatForUpdate(@Param("seatId") Long seatId);
}
