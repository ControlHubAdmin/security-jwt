package com.authcheck.controller;


import com.authcheck.dto.*;
import com.authcheck.entities.User;
import com.authcheck.services.AuthenticationService;
import com.authcheck.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @Autowired
    private UserService userService;


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
        System.out.println("cominggg");
       JwtAuthenticationResponse response =  authenticationService.signIn(signInRequest);
        System.out.println(response.getToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        System.out.println("Post coming");
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }


}
