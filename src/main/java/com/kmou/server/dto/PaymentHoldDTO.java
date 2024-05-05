package com.kmou.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentHoldDTO {
    private String orderId;
    private Long postId;
    private String name;
    private String amount;
    private String orderName;
}
