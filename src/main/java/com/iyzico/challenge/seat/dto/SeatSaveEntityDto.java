package com.iyzico.challenge.seat.dto;

import com.iyzico.challenge.seat.enums.SeatStatus;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class SeatSaveEntityDto {
    private Long flightId;
    private String seatNumber;
    private BigDecimal price;
    private SeatStatus status;
}
