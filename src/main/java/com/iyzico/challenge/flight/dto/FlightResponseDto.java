package com.iyzico.challenge.flight.dto;

import com.iyzico.challenge.seat.dto.SeatResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class FlightResponseDto {

    private String name;
    private String description;
    private List<SeatResponseDto> seats;
}
