package com.authcheck.controller;

import com.authcheck.entities.User;
import com.authcheck.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        System.out.println("Cominggggggg");

        return ResponseEntity.ok("Hii user");
    }




}
