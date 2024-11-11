package com.iyzico.challenge.flight.service;

import com.iyzico.challenge.flight.dto.FlightDto;
import com.iyzico.challenge.flight.dto.FlightSaveEntityDto;
import com.iyzico.challenge.flight.entity.Flight;
import com.iyzico.challenge.flight.repository.FligthRepository;
import com.iyzico.challenge.seat.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FlightServiceTest {
    @Mock
    private FligthRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private FlightService flightService;

    private FlightSaveEntityDto saveEntityDto;
    private Flight flight;
    private Flight savedFlight;
    private FlightDto flightDto;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        saveEntityDto = new FlightSaveEntityDto();
        flight = new Flight();
        savedFlight = new Flight();
        savedFlight.setId(1L);
        flightDto = new FlightDto();
        when(modelMapper.map(saveEntityDto, Flight.class)).thenReturn(flight);
        when(modelMapper.map(savedFlight, FlightDto.class)).thenReturn(flightDto);

        when(flightRepository.save(flight)).thenReturn(savedFlight);
    }

    @Test
    public void testSaveFlight() {
        FlightDto result = flightService.saveFlight(saveEntityDto);

        verify(flightRepository, times(1)).save(flight);
        verify(modelMapper, times(1)).map(saveEntityDto, Flight.class);
        verify(modelMapper, times(1)).map(savedFlight, FlightDto.class);
        verify(seatService, times(1)).createInitialSeatForFlight(savedFlight.getId());

        assertNotNull(result);
        assertEquals(flightDto, result);
    }

    @Test
    public void testDeleteFlight() {
        Long flightId = 1L;

        flightService.deleteFlight(flightId);

        verify(flightRepository, times(1)).deleteById(flightId);
    }

}
