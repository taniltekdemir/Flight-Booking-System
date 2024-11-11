package com.iyzico.challenge.seat.controller;

import com.iyzico.challenge.seat.dto.SeatDTO;
import com.iyzico.challenge.seat.dto.SeatSaveEntityDto;
import com.iyzico.challenge.seat.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(description = "/seats", name = "Seat API")
@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping()
    @Operation(summary = "Create new seat data")
    public ResponseEntity<?> createNewSeat(@Valid @RequestBody SeatSaveEntityDto saveEntityDto) {
        try {
            SeatDTO newSeat = seatService.saveSeat(saveEntityDto);
            URI location = new URI("/seats/" + newSeat.getId());
            return ResponseEntity.created(location).body(newSeat);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }

    @PutMapping()
    @Operation(summary = "Update seat")
    public ResponseEntity<?> updateSeat(@Valid @RequestBody SeatDTO updateDto){
        try {
            seatService.updateSeat(updateDto);
            return ResponseEntity.ok("ok");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete with seatId")
    public ResponseEntity<?> deleteFlight(@PathVariable Long id){
        try {
            seatService.deleteSeat(id);
            return ResponseEntity.ok("ok");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }
}
