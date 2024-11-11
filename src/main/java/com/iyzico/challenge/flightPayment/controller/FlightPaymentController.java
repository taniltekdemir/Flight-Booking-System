package com.iyzico.challenge.flightPayment.controller;

import com.iyzico.challenge.flightPayment.BookingService;
import com.iyzico.challenge.flightPayment.FlightPaymentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(description = "/fligthPayment", name = "Fligth Payment API")
@RestController
@RequestMapping("/fligthPayment")
public class FlightPaymentController {
    @Autowired
    private BookingService bookingService;


    @PostMapping()
    @Operation(summary = "Create new flight payment")
    public ResponseEntity<?> paymentSeat(@Valid @RequestBody FlightPaymentDto flightPaymentDto) {
        try {
            bookingService.bookingPaymentSeat(flightPaymentDto.getSeatId(), flightPaymentDto.getUserId());
            return ResponseEntity.ok("Success");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }


}
