package com.kmou.server.controller;

import com.kmou.server.dto.PaymentHoldDTO;
import com.kmou.server.dto.PaymentRequestDTO;
import com.kmou.server.entity.TossPayment;
import com.kmou.server.service.PaymentService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    RestTemplate restTemplate = new RestTemplate();

    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/confirm")
    public TossPayment confirmPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        logger.info("Payment confirm request received: {}", paymentRequestDTO);

        String paymentKey = paymentRequestDTO.getPaymentKey();
        String orderId = paymentRequestDTO.getOrderId();
        String amount = paymentRequestDTO.getAmount();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + getEncodedAuthorizationValue());

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        paymentService.changeAccepted(orderId);
        logger.info("Payment accepted: {}", orderId);

        HttpEntity<String> entity = new HttpEntity<>(obj.toJSONString(), headers);

        TossPayment response = restTemplate.postForObject(
                "https://api.tosspayments.com/v1/payments/confirm",
                entity,
                TossPayment.class);

        return response;
    }

    private String getEncodedAuthorizationValue() {
        String widgetSecretKey = "test_sk_6BYq7GWPVv9JQx61k1Nl3NE5vbo1";
        return Base64.getEncoder().encodeToString((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
    }



    @PostMapping("/hold")
    public ResponseEntity<PaymentHoldDTO> holdPayment(@RequestBody PaymentHoldDTO paymentHoldDTO) {
        logger.info("Payment hold request received: {}", paymentHoldDTO.toString());

        paymentService.save(paymentHoldDTO);

        return ResponseEntity.ok(paymentHoldDTO);
    }

}
