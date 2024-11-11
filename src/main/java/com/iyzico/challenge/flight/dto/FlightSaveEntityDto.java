package com.iyzico.challenge.flight.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class FlightSaveEntityDto {
    @NotNull
    private String flightName;
    @NotNull
    private String description;
    @NotNull
    private LocalDate departureTime;
    @NotNull
    private LocalDate arrivalTime;

}
