package com.iyzico.challenge.flightPayment;

import com.iyzico.challenge.common.exception.BusinessException;
import com.iyzico.challenge.seat.entity.Seat;
import com.iyzico.challenge.seat.enums.SeatStatus;
import com.iyzico.challenge.seat.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookingService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FlightPaymentService flightPaymentService;
    @Transactional
    public void bookingPaymentSeat(Long seatId, Long userId) {
        Seat seat = seatRepository.findAvailableSeatForUpdate(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        boolean paymentSuccess = flightPaymentService.processPayment(seat, userId);

        if (paymentSuccess) {
            seat.setStatus(SeatStatus.SOLD);
            seatRepository.save(seat);
        } else {
            throw new BusinessException("Payment failed, so do not update the seat status");
        }
    }
}
