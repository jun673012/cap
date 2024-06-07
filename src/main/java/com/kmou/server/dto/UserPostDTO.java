package com.kmou.server.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDTO {
    Long Id;
    String garbageName;
    String username;
    String time;
    String address;
    String phoneNumber;
    Long price;
    byte[] image;
    boolean isAccepted;
    boolean isPaid;
}
