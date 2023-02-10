package com.sutej.QuickHire.Controllers;

import com.sutej.QuickHire.Dto.AuthenticationResponse;
import com.sutej.QuickHire.Dto.LoginRequest;
import com.sutej.QuickHire.Dto.RegisterRequest;
import com.sutej.QuickHire.Services.AuthenticationServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServices authenticationServices;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> userRegistration(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<AuthenticationResponse>(
                authenticationServices.userRegistration(registerRequest),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> userLogin(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(
                authenticationServices.userLogin(loginRequest),
                HttpStatus.OK
        );
    }

}
