package com.iyzico.challenge.flight.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iyzico.challenge.seat.entity.Seat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "flight_name")
    private String flightName;
    @Column(name = "description")
    private String description;
    @Column(name = "departure_time")
    private LocalDate departureTime;
    @Column(name = "arrival_time")
    private LocalDate arrivalTime;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Seat> seats = new ArrayList<>();

}

