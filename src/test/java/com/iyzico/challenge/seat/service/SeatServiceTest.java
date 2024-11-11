package com.iyzico.challenge.seat.service;

import com.iyzico.challenge.common.exception.BusinessException;
import com.iyzico.challenge.flight.repository.FligthRepository;
import com.iyzico.challenge.seat.dto.SeatDTO;
import com.iyzico.challenge.seat.dto.SeatSaveEntityDto;
import com.iyzico.challenge.seat.entity.Seat;
import com.iyzico.challenge.seat.enums.SeatStatus;
import com.iyzico.challenge.seat.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class SeatServiceTest {
    @Mock
    private SeatRepository seatRepository;

    @Mock
    private FligthRepository flightRepository;

    @InjectMocks
    private SeatService seatService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteSeat_SeatFoundAndNotSold() {
        Long seatId = 1L;
        Seat seat = new Seat();
        seat.setId(seatId);
        seat.setStatus(SeatStatus.AVAILABLE);

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        seatService.deleteSeat(seatId);

        verify(seatRepository, times(1)).findById(seatId);
        verify(flightRepository, times(1)).deleteById(seatId);
    }

    @Test
    public void testDeleteSeat_SeatNotFound() {
        Long seatId = 1L;

        when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            seatService.deleteSeat(seatId);
        });

        assertEquals("Seat not found", exception.getMessage());
        verify(seatRepository, times(1)).findById(seatId);
        verify(flightRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteSeat_SeatSold() {
        Long seatId = 1L;
        Seat seat = new Seat();
        seat.setId(seatId);
        seat.setStatus(SeatStatus.SOLD);

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            seatService.deleteSeat(seatId);
        });

        assertEquals("Seat was sold. It could not delete", exception.getMessage());
        verify(seatRepository, times(1)).findById(seatId);
        verify(flightRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testSaveSeat_SeatNotExists() {
        SeatSaveEntityDto saveEntityDto = new SeatSaveEntityDto();
        saveEntityDto.setFlightId(1L);
        saveEntityDto.setSeatNumber("T-1");

        Seat seat = new Seat();
        Seat newRecord = new Seat();
        SeatDTO seatDTO = new SeatDTO();

        when(seatRepository.findFirstByFlightIdAndSeatNumber(saveEntityDto.getFlightId(), saveEntityDto.getSeatNumber())).thenReturn(null);
        when(modelMapper.map(saveEntityDto, Seat.class)).thenReturn(seat);
        when(seatRepository.save(seat)).thenReturn(newRecord);
        when(modelMapper.map(newRecord, SeatDTO.class)).thenReturn(seatDTO);

        SeatDTO result = seatService.saveSeat(saveEntityDto);

        verify(seatRepository, times(1)).findFirstByFlightIdAndSeatNumber(saveEntityDto.getFlightId(), saveEntityDto.getSeatNumber());
        verify(seatRepository, times(1)).save(seat);
        verify(modelMapper, times(1)).map(saveEntityDto, Seat.class);
        verify(modelMapper, times(1)).map(newRecord, SeatDTO.class);

        assertNotNull(result);
        assertEquals(seatDTO, result);
    }

    @Test
    public void testSaveSeat_SeatAlreadyExists() {
        SeatSaveEntityDto saveEntityDto = new SeatSaveEntityDto();
        saveEntityDto.setFlightId(1L);
        saveEntityDto.setSeatNumber("T-1");

        Seat existSeat = new Seat();

        when(seatRepository.findFirstByFlightIdAndSeatNumber(saveEntityDto.getFlightId(), saveEntityDto.getSeatNumber())).thenReturn(existSeat);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            seatService.saveSeat(saveEntityDto);
        });

        assertEquals("Same seatNumber is exist on this Flight", exception.getMessage());
        verify(seatRepository, times(1)).findFirstByFlightIdAndSeatNumber(saveEntityDto.getFlightId(), saveEntityDto.getSeatNumber());
        verify(seatRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    public void testUpdateSeat() {
        SeatDTO seatDTO = new SeatDTO();
        Seat seat = new Seat();
        Seat updatedSeat = new Seat();
        SeatDTO updatedSeatDTO = new SeatDTO();

        when(modelMapper.map(seatDTO, Seat.class)).thenReturn(seat);
        when(seatRepository.save(seat)).thenReturn(updatedSeat);
        when(modelMapper.map(updatedSeat, SeatDTO.class)).thenReturn(updatedSeatDTO);

        SeatDTO result = seatService.updateSeat(seatDTO);

        verify(modelMapper, times(1)).map(seatDTO, Seat.class);
        verify(seatRepository, times(1)).save(seat);
        verify(modelMapper, times(1)).map(updatedSeat, SeatDTO.class);

        assertNotNull(result);
        assertEquals(updatedSeatDTO, result);
    }
}
