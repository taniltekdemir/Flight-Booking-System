package com.iyzico.challenge.flight.dto;

import com.iyzico.challenge.seat.dto.SeatDTO;
import lombok.Data;

import java.util.List;
@Data
public class FlightSeatsDto {
    FlightDto flight;
    List<SeatDTO> seatList;
}
