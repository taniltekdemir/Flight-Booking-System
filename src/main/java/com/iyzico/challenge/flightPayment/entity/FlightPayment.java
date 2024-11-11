package com.iyzico.challenge.flightPayment.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "flight_payment")
@Data
public class FlightPayment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "seat_id")
    private Long seatId;

    @Column(precision = 19, scale = 0, name = "price")
    private BigDecimal price;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "payment_time")
    private LocalDate paymentTime;
}
