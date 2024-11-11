package com.iyzico.challenge.flight.repository;

import com.iyzico.challenge.flight.entity.Flight;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FligthRepository extends JpaRepository<Flight, Long> {
    @EntityGraph(attributePaths = "seats")
    List<Flight> findAll();
    @Query("SELECT f FROM Flight f JOIN FETCH f.seats s WHERE f.id = :flightId and s.status = 'AVAILABLE'")
    Flight getFlightSeatByFlightId(@Param("flightId") Long flightId);
}
