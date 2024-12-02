package com.iyzico.challenge.flightPayment;

import com.iyzico.challenge.common.exception.PaymentException;
import com.iyzico.challenge.flightPayment.entity.FlightPayment;
import com.iyzico.challenge.flightPayment.entity.PaymentStatus;
import com.iyzico.challenge.seat.entity.Seat;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class FlightPaymentService {
    @Autowired
    private FlightPaymentRepository flightPaymentRepository;

    public boolean processPayment(Seat seat, Long userId) {
        com.iyzipay.model.Payment paymentResponse = iyzipayIntegration(seat, userId);
        if (Objects.equals(paymentResponse.getStatus(), "failure")) {
            throw new PaymentException(paymentResponse.getErrorMessage(), paymentResponse.getErrorCode());
        }
        FlightPayment newPayment = new FlightPayment();
        newPayment.setStatus(PaymentStatus.PAID);
        newPayment.setPrice(seat.getPrice());
        newPayment.setSeatId(seat.getId());
        newPayment.setPaymentTime(LocalDate.now());
        newPayment.setUserId(userId);

        flightPaymentRepository.save(newPayment);
        return true;
    }
    public com.iyzipay.model.Payment iyzipayIntegration(Seat seat, Long userId) {

        Options options = new Options();
        options.setApiKey("");
        options.setSecretKey("");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId("123456789");
        request.setPrice(seat.getPrice());
        request.setPaidPrice(seat.getPrice());
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("Ince Mehmet");
        if (userId == 555) {
            paymentCard.setCardNumber("55287900000000081");
        } else {
            paymentCard.setCardNumber("5528790000000008");
        }
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName("mehmet");
        buyer.setSurname("sahan");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Cukurova, Aladag Mah. Hatice Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Adana");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName("mehmet sahan");
        shippingAddress.setCity("Adana");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Cukurova, Aladag Mah. Hatice Sok. No:1");
        shippingAddress.setZipCode("34742");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName("mehmet sahan");
        billingAddress.setCity("Adana");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Cukurova, Aladag Mah. Hatice Sok. No:1");
        billingAddress.setZipCode("34742");
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = new ArrayList<>();
        BasketItem firstBasketItem = new BasketItem();
        firstBasketItem.setId("BI101");
        firstBasketItem.setName("Binocular");
        firstBasketItem.setCategory1("Collectibles");
        firstBasketItem.setCategory2("Accessories");
        firstBasketItem.setItemType(BasketItemType.PHYSICAL.name());
        firstBasketItem.setPrice(seat.getPrice());
        basketItems.add(firstBasketItem);

        request.setBasketItems(basketItems);

        return Payment.create(request, options);
    }
}
