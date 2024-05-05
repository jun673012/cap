package com.kmou.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPayment {
    private String orderName;
    private String method;
    private String totalAmount;
    private String paymentKey;
    private String orderId;

}