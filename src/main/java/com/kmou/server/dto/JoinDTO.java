package com.kmou.server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {
    private String username;
    private String name;
    private String password;
    private Long phoneNumber;
}
