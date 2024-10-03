package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.authservices.JwtService;
import com.coupongenerator.admin.dtos.LoginRequestDto;
import com.coupongenerator.admin.dtos.LoginResponseDto;
import com.coupongenerator.admin.entities.AdminDetails;
import com.coupongenerator.admin.services.AuthenticationService;
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

    @PostMapping("/signup")
    public ResponseEntity<?> register() {
        AdminDetails registeredUser = authenticationService.signup();

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginRequestDto requestDto
    ) {
        AdminDetails authenticatedUser = authenticationService.authenticate(requestDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

//        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        LoginResponseDto responseDto = new LoginResponseDto(jwtToken);

        return ResponseEntity.ok(responseDto);
    }
}
