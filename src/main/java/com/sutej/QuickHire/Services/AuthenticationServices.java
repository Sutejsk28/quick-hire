package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.AuthenticationResponse;
import com.sutej.QuickHire.Dto.LoginRequest;
import com.sutej.QuickHire.Dto.RegisterRequest;
import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Enums.Roles;
import com.sutej.QuickHire.Repository.UserRepository;
import com.sutej.QuickHire.Security.JwtProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Data
@RequiredArgsConstructor
public class AuthenticationServices {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse userRegistration(RegisterRequest registerRequest) {
        UserEntity user =  new UserEntity();

        user.setUsername(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAddress(registerRequest.getAddress());
        user.setCity(registerRequest.getCity());
        user.setCountry(registerRequest.getCountry());
        user.setCreatedTime(Instant.now());
        user.setRoles(Roles.USER);

        userRepository.save(user);
        String jwt = getJwtProvider().generateToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwt)
                .build();
    }

    public AuthenticationResponse userLogin(LoginRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserEntity user = userRepository
                                    .findByUsername(loginRequest.getUsername())
                                            .orElseThrow();

        String jwt = getJwtProvider().generateToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwt)
                .build();
    }

}
