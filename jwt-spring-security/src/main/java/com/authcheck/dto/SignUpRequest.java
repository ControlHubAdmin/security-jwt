package com.authcheck.dto;

import lombok.*;

@Data
public class SignUpRequest {

    private String username;
    private String email;
    private String phone;
    private String password;

}
