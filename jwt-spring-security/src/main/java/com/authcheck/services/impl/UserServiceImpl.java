package com.authcheck.services.impl;

import com.authcheck.dto.UpdateUserRequest;
import com.authcheck.entities.Role;
import com.authcheck.entities.User;
import com.authcheck.repository.UserRepository;
import com.authcheck.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)  {
                return userRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
       Optional<User> optionalUser =  userRepository.findByEmail(email);

        return optionalUser.isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().filter(user -> user.getRole()!= Role.ADMIN).toList();
    }

//    @Override
//    public User updateUser(Integer userId, UpdateUserRequest updateUserRequest)  {
//        Optional<User> optionalUser = userRepository.findById(userId);
//
//        // Update only the necessary fields (email and phone in this case)
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//
//            // Update only the necessary fields (email and phone in this case)
//            user.setEmail(updateUserRequest.getEmail());
//            user.setPhone(updateUserRequest.getPhone());
//
//            // Save the updated user
//            return userRepository.save(user);
//        } else {
//            // Handle the case when the user with the given ID is not found
//            throw new IllegalArgumentException("sdn");
//        }
//    }

}
