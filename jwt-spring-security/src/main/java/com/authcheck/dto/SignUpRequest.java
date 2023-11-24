package com.authcheck.dto;

import lombok.*;

@Data
public class SignUpRequest {

    private String firstName;
    private String secondName;
    private String email;
    private String password;

}
