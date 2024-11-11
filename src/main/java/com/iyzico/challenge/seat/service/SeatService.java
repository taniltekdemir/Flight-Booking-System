package com.iyzico.challenge.seat.service;

import com.iyzico.challenge.common.exception.BusinessException;
import com.iyzico.challenge.common.exception.FlightNotFoundException;
import com.iyzico.challenge.flight.entity.Flight;
import com.iyzico.challenge.flight.repository.FligthRepository;
import com.iyzico.challenge.seat.dto.SeatDTO;
import com.iyzico.challenge.seat.dto.SeatSaveEntityDto;
import com.iyzico.challenge.seat.entity.Seat;
import com.iyzico.challenge.seat.enums.SeatStatus;
import com.iyzico.challenge.seat.repository.SeatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FligthRepository fligthRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<SeatDTO> createInitialSeatForFlight(Long flightId) {

        Flight flight = fligthRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("error.flight.not.found", "Flight not found with id ".concat(flightId.toString())));
        List<SeatDTO> createdSeats = new ArrayList<>();
        String[] aisleName = {"A", "B", "C", "D", "E", "F"};
        for (Integer i = 1; i <= 10; i++) {
            final Integer seatRow = i;
            Arrays.stream(aisleName).forEach(item -> {
                Seat newRecord = new Seat();
                newRecord.setSeatNumber(item + "-" +seatRow.toString());
                newRecord.setFlight(flight);
//                newRecord.setFlightId(flight.getId());
                newRecord.setPrice(BigDecimal.valueOf(100));
                newRecord.setStatus(SeatStatus.AVAILABLE);

                createdSeats.add(modelMapper.map(seatRepository.save(newRecord), SeatDTO.class));
            });
        }
        return  createdSeats;
    }

    public SeatDTO updateSeat(SeatDTO seatDTO) {
        Seat seat = modelMapper.map(seatDTO, Seat.class);
        Seat updatedSeat = seatRepository.save(seat);
        return modelMapper.map(updatedSeat, SeatDTO.class);
    }

    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new BusinessException("Seat not found"));
        if (seat.getStatus().equals(SeatStatus.SOLD)) {
            throw new BusinessException("Seat was sold. It could not delete");
        }
        fligthRepository.deleteById(id);
    }

    public SeatDTO saveSeat(SeatSaveEntityDto saveEntityDto) {
        Seat existSeat = seatRepository.findFirstByFlightIdAndSeatNumber(saveEntityDto.getFlightId(),saveEntityDto.getSeatNumber());
        if (existSeat != null) {
            throw new BusinessException("Same seatNumber is exist on this Flight");
        }
        Seat seat = modelMapper.map(saveEntityDto, Seat.class);
        Seat newrecord = seatRepository.save(seat);
        return modelMapper.map(newrecord, SeatDTO.class);
    }

}
