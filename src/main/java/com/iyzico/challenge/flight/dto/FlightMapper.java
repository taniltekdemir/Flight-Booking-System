package com.iyzico.challenge.flight.dto;

import com.iyzico.challenge.flight.entity.Flight;
import com.iyzico.challenge.seat.dto.SeatResponseDto;
import com.iyzico.challenge.seat.entity.Seat;

import java.util.List;
import java.util.stream.Collectors;

public class FlightMapper {
    public static FlightResponseDto convertToFlightDTO(Flight flight) {
        FlightResponseDto flightDTO = new FlightResponseDto();
        flightDTO.setName(flight.getFlightName());
        flightDTO.setDescription(flight.getDescription());

        List<SeatResponseDto> seatDTOs = flight.getSeats().stream()
                .map(FlightMapper::convertToSeatDTO)
                .collect(Collectors.toList());
        flightDTO.setSeats(seatDTOs);

        return flightDTO;
    }

    public static SeatResponseDto convertToSeatDTO(Seat seat) {
        SeatResponseDto seatDTO = new SeatResponseDto();
        seatDTO.setSeatNumber(seat.getSeatNumber());
        seatDTO.setPrice(seat.getPrice());
        return seatDTO;
    }
}