package com.authcheck.services;

import com.authcheck.dto.SignUpRequest;
import com.authcheck.entities.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
}
