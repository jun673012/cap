package com.kmou.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBodyShowDTO {
    Long Id;
    String address;
    byte[] image;
    String userName;
    String userId;
    Long price;
    String garbageName;
    String garbageContent;
    boolean isAccepted;
    boolean isPaid;
    String time;
}
