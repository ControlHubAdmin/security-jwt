package com.authcheck.controller;


import com.authcheck.dto.SignUpRequest;
import com.authcheck.entities.User;
import com.authcheck.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
        System.out.println("coming");
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getAllUser(){
        return ResponseEntity.ok("hai");
    }
}
