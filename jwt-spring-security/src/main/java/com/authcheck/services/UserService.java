package com.authcheck.services;

import com.authcheck.dto.UpdateUserRequest;
import com.authcheck.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();

    boolean isEmailAlreadyExists(String email);

    List<User> getAllUsers();

    void deleteUser(Integer userId);

//    User updateUser(Integer userId, UpdateUserRequest updateUserRequest);


}
