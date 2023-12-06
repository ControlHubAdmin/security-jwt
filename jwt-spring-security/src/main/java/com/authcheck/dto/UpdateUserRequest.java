package com.authcheck.dto;

import com.authcheck.entities.User;
import lombok.Data;

@Data
public class UpdateUserRequest {

   private String username;
   private String email;
   private String phone;
   private String role;
}
