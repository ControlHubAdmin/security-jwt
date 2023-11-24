package com.authcheck.services;

import com.authcheck.dto.JwtAuthenticationResponse;
import com.authcheck.dto.RefreshTokenRequest;
import com.authcheck.dto.SignInRequest;
import com.authcheck.dto.SignUpRequest;
import com.authcheck.entities.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
