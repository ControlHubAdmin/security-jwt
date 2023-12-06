package com.authcheck.controller;


import com.authcheck.dto.*;
import com.authcheck.entities.Role;
import com.authcheck.entities.User;
import com.authcheck.repository.UserRepository;
import com.authcheck.services.AuthenticationService;
import com.authcheck.services.JWTService;
import com.authcheck.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PostExchange;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/signup")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ApiResponses> signup(@RequestBody SignUpRequest signUpRequest){
        System.out.println("coming");
        System.out.println(signUpRequest.getEmail());

        try {
            ApiResponses responses = new ApiResponses();
            if(userService.isEmailAlreadyExists(signUpRequest.getEmail())){
                responses.setStatus("Failed");
                responses.setMessage("Email already exists ,Try using another one");
                return ResponseEntity.badRequest().body(responses);
            }
            User newUser = authenticationService.signup(signUpRequest);
            responses.setStatus("success");
            responses.setMessage("User successfully registered");
            return ResponseEntity.ok(responses);

        }
        catch (Exception e){
            ApiResponses exceptionRes = new ApiResponses();
            exceptionRes.setStatus("error");
            exceptionRes.setMessage("Failed user request to register"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionRes);
        }
    }


    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        System.out.println(signInRequest.getEmail());
        System.out.println(signInRequest.getPassword());
        System.out.println("cominggghh");
        JwtAuthenticationResponse response =  authenticationService.signIn(signInRequest);

        System.out.println(response.getToken()+"vsjdvjsn");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-users")
    public  ResponseEntity<List<User>> getAllUsers(){
        List <User> users  = userService.getAllUsers();
        return  ResponseEntity.ok(users);
    };

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        System.out.println("Post coming");
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }




    @PutMapping("/edit-user/{id}")
    public ResponseEntity<User> editUser(@RequestBody UpdateUserRequest updateUserRequest,
                                         @PathVariable String id, HttpServletRequest request) {
        System.out.println("edit coming");

        Integer userId = Integer.parseInt(id);
        System.out.println("iddd"+userId);

        // Extract token from request headers
        String token = request.getHeader("Authorization").split(" ")[1];

        System.out.println("cominghhjj"+token);
        // Validate the token
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update user details based on the data in updateUserRequest
            existingUser.setUsername(updateUserRequest.getUsername());
            existingUser.setEmail(updateUserRequest.getEmail());
            existingUser.setPhone(updateUserRequest.getPhone());
            existingUser.setRole(Role.valueOf(updateUserRequest.getRole().toUpperCase()));

            // Save the updated user to the database
            User updatedUser = userRepository.save(existingUser);

            // Return the updated user in the response
            return ResponseEntity.ok(updatedUser);
        } else {
            // Handle the case where the user with the given ID is not found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id,HttpServletRequest request){
        Integer userId = Integer.parseInt(id);
        String token = request.getHeader("Authorization").split(" ")[1];
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Deleted");
        }

    }



    @PutMapping("")
    public void test(){

    }


    @PostMapping("/update-profile-picture/{userId}")
    public ResponseEntity<User> updateProfilePicture(
            @PathVariable String userId,
            @RequestParam("profilePicture") MultipartFile profilePicture,HttpServletRequest request) {

        Integer id = Integer.parseInt(userId);

        String token = request.getHeader("Authorization").split(" ")[1];
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {

            String profilePicUrl = saveProfilePicture(id, profilePicture);

            // Update the user's profilePicUrl in the database
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setProfilePicUrl(profilePicUrl);
                userRepository.save(user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            // Handle the exception (e.g., log it or return an error response)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private String saveProfilePicture(Integer userId, MultipartFile profilePicture) throws IOException {

        String uploadDirectory = "uploads";
        String fileName = userId + "_" + profilePicture.getOriginalFilename();
        String filePath = Paths.get(uploadDirectory, fileName).toString();

        // Save the file
        Files.copy(profilePicture.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Return the URL to the saved profile picture
        return "C:\\security-jwt\\uploads" + fileName; // Adjust the URL based on your setup
    }





}
