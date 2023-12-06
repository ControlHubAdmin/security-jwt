package com.authcheck.controller;

import com.authcheck.dto.UpdateUserRequest;
import com.authcheck.entities.User;
import com.authcheck.repository.UserRepository;
import com.authcheck.services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;


    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hii admin");
    }




}
