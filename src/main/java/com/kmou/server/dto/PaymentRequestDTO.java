package com.kmou.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequestDTO {
    private String paymentKey;
    private String orderId;
    private String amount;
}
