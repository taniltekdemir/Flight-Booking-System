package com.iyzico.challenge.flightPayment;

import com.iyzico.challenge.flightPayment.entity.FlightPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FlightPaymentRepository extends JpaRepository<FlightPayment, Long>{
}

