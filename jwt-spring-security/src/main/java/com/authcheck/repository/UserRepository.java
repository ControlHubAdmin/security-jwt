package com.authcheck.repository;


import com.authcheck.entities.Role;
import com.authcheck.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String username);

    User findByRole(Role role);
}
