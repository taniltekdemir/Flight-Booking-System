package com.iyzico.challenge.flightPayment.service;

import com.iyzico.challenge.flight.entity.Flight;
import com.iyzico.challenge.flight.repository.FligthRepository;
import com.iyzico.challenge.flightPayment.BookingService;
import com.iyzico.challenge.flightPayment.FlightPaymentService;
import com.iyzico.challenge.seat.entity.Seat;
import com.iyzico.challenge.seat.enums.SeatStatus;
import com.iyzico.challenge.seat.repository.SeatRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightPaymentServiceTest {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private FligthRepository fligthRepository;
    @Autowired
    private SeatRepository seatRepository;
    private Long testSeatId;
    private Seat mockSeat;
    @BeforeEach
    public void setUp() {

        Flight flight = new Flight();
        flight.setFlightName("mock_flight");
        flight.setDescription("mock_description");
        flight.setArrivalTime(LocalDate.now());
        flight.setDepartureTime(LocalDate.now());
        Flight savedFlight = fligthRepository.save(flight);
        Seat seat = new Seat();
        seat.setSeatNumber("A-1");
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setPrice(BigDecimal.ZERO);
        seat.setFlight(savedFlight);
        Seat savedSeat = seatRepository.save(seat);
        testSeatId = savedSeat.getId();
        mockSeat = savedSeat;

        when(flightPaymentService.processPayment(any(Seat.class), eq(1L))).thenReturn(true);
        when(flightPaymentService.processPayment(any(Seat.class), eq(2L))).thenReturn(true);
    }

    @AfterEach
    public void cleanUp() {
        if (testSeatId != null) {
            seatRepository.deleteById(testSeatId);
        }
    }
    @MockBean
    private FlightPaymentService flightPaymentService;

    @Test
    public void testConcurrentSeatPurchase() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable task1 = () -> {
            try {
                latch.await();
                bookingService.bookingPaymentSeat(mockSeat.getId(),1L);
            } catch (Exception e) {
                System.out.println("Thread 1 - Exception: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                latch.await();
                bookingService.bookingPaymentSeat(mockSeat.getId(),2L);
            } catch (Exception e) {
                System.out.println("Thread 2 - Exception: " + e.getMessage());
            }
        };

        executorService.submit(task1);
        executorService.submit(task2);

        latch.countDown();
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);


        Seat seat = seatRepository.findById(testSeatId).orElseThrow(() -> new RuntimeException("Seat not found"));
        assertEquals(SeatStatus.SOLD, seat.getStatus(), "The seat status should be SOLD");
    }
}
