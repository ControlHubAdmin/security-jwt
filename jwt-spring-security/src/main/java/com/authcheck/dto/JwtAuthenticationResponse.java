package com.authcheck.dto;

import com.authcheck.entities.User;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;
    private String refreshToken;
    private User user;
}
