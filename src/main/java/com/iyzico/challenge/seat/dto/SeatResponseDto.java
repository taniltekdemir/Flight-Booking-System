package com.iyzico.challenge.seat.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatResponseDto {
    private String seatNumber;

    private BigDecimal price;
}
