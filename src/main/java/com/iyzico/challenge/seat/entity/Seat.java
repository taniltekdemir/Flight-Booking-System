package com.iyzico.challenge.seat.entity;

import com.fasterxml.jackson.annotation.*;
import com.iyzico.challenge.flight.entity.Flight;
import com.iyzico.challenge.seat.enums.SeatStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Flight flight;


    @Column(name = "seat_number")
    private String seatNumber;

    @Column(precision = 19, scale = 0, name = "price")
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
