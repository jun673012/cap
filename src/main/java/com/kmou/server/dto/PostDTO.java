package com.kmou.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long Id;
    private String address;
    private byte[] image;
    private String username;
    private String userId;
    private Long price;
    private String garbageName;
    private String garbageContent;
    private String time;
}
