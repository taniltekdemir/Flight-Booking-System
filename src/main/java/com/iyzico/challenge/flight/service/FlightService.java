package com.iyzico.challenge.flight.service;

import com.iyzico.challenge.common.exception.FlightNotFoundException;
import com.iyzico.challenge.flight.dto.*;
import com.iyzico.challenge.flight.entity.Flight;
import com.iyzico.challenge.flight.repository.FligthRepository;
import com.iyzico.challenge.seat.repository.SeatRepository;
import com.iyzico.challenge.seat.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.iyzico.challenge.flight.dto.FlightMapper.convertToFlightDTO;

@Service
@Transactional
public class FlightService {
    @Autowired
    private FligthRepository fligthRepository;
    @Autowired
    private SeatService seatService;
    private ModelMapper modelMapper = new ModelMapper();

    public FlightDto saveFlight(FlightSaveEntityDto saveEntityDto) {
        Flight flight = modelMapper.map(saveEntityDto, Flight.class);
        Flight newrecord = fligthRepository.save(flight);
        seatService.createInitialSeatForFlight(newrecord.getId());
        return modelMapper.map(newrecord, FlightDto.class);
    }

    public void deleteFlight(Long id) {
        fligthRepository.deleteById(id);
    }

    public FlightDto updateFlight(FlightDto flightDto) {
        Flight flight = modelMapper.map(flightDto, Flight.class);
        Flight updatedFlight = fligthRepository.save(flight);
        return modelMapper.map(updatedFlight, FlightDto.class);
    }

    public List<FlightResponseDto> getAllFlightSeats() {
        List<Flight> allFlightSeats = fligthRepository.findAll();
        return allFlightSeats.stream()
                .map(FlightMapper::convertToFlightDTO)
                .collect(Collectors.toList());
    }

    public FlightResponseDto getFlightSeatByFlightId(Long flightId) {
        Flight flightSeats = fligthRepository.getFlightSeatByFlightId(flightId);
        if (flightSeats == null) {
            throw new FlightNotFoundException("error.flight.not.found", "Flight not found with id ".concat(flightId.toString()));
        }
        return convertToFlightDTO(flightSeats);
    }
}
