package com.iyzico.challenge.flight.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class FlightDto {

    private Long id;
    private String flightName;
    private String description;
    private LocalDate departureTime;
    private LocalDate arrivalTime;
}
