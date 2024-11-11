package com.iyzico.challenge.flight.controller;

import com.iyzico.challenge.flight.dto.FlightDto;
import com.iyzico.challenge.flight.dto.FlightSaveEntityDto;
import com.iyzico.challenge.flight.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(description = "/fligths", name = "Fligth API")
@RestController
@RequestMapping("/fligths")
public class FlightController {
    @Autowired
    private FlightService flightService;
    @GetMapping()
    @Operation(summary = "Fetch all exist fligth data")
    public ResponseEntity<?> getFlightSeats() {
        try {
            return ResponseEntity.ok(flightService.getAllFlightSeats());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch one fligth data by flightId")
    public ResponseEntity<?> getFlightSeatsByFlightId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(flightService.getFlightSeatByFlightId(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }

    @PostMapping()
    @Operation(summary = "Create new flight data")
    public ResponseEntity<?> createNewFlight(@Valid @RequestBody FlightSaveEntityDto saveEntityDto) {
        try {
            FlightDto newFlight = flightService.saveFlight(saveEntityDto);
            URI location = new URI("/flights/" + newFlight.getId());
            return ResponseEntity.created(location).body(newFlight);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }

    @PutMapping()
    @Operation(summary = "Update flight")
    public ResponseEntity<?> updateFlight(@Valid @RequestBody FlightDto updateDto){
        try {
            flightService.updateFlight(updateDto);
            return ResponseEntity.ok("ok");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete with flightId")
    public ResponseEntity<?> deleteFlight(@PathVariable Long id){
        try {
            flightService.deleteFlight(id);
            return ResponseEntity.ok("ok");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }
}
