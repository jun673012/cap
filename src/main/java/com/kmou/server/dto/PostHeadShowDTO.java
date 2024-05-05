package com.kmou.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostHeadShowDTO {
    Long Id;
    String garbageName;
    String username;
    String time;
    boolean isAccepted;
    boolean isPaid;
}
