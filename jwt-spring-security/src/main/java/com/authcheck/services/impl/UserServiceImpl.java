package com.authcheck.services.impl;

import com.authcheck.entities.User;
import com.authcheck.repository.UserRepository;
import com.authcheck.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
