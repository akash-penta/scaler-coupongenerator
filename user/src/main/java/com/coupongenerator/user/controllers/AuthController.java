package com.coupongenerator.user.controllers;

import com.coupongenerator.user.authservices.JwtService;
import com.coupongenerator.user.dtos.LoginResponseDto;
import com.coupongenerator.user.dtos.LoginRequestDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginRequestDto requestDto
    ) {
        User authenticatedUser = authenticationService.authenticate(requestDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

//        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        LoginResponseDto responseDto = new LoginResponseDto(jwtToken);

        return ResponseEntity.ok(responseDto);
    }
}
