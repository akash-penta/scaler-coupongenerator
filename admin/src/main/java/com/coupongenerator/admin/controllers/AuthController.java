package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.authservices.JwtService;
import com.coupongenerator.admin.dtos.ExceptionDto;
import com.coupongenerator.admin.dtos.LoginRequestDto;
import com.coupongenerator.admin.dtos.LoginResponseDto;
import com.coupongenerator.admin.entities.AdminDetails;
import com.coupongenerator.admin.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
            @Valid @RequestBody LoginRequestDto requestDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            Set<String> errorSet = new HashSet<>();
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errorSet.add(fieldError.getDefaultMessage());
            });

            if(errorSet.contains("User name is mandatory")) {
                errorMap.put("userName", "User name is mandatory");
            }
            else if(errorSet.contains("User name should not be blank")) {
                errorMap.put("userName", "User name should not be blank");
            }

            if(errorSet.contains("Password is mandatory")) {
                errorMap.put("password", "Password is mandatory");
            }
            else if(errorSet.contains("Password should not be blank")) {
                errorMap.put("password", "Password should not be blank");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }
        AdminDetails authenticatedUser = authenticationService.authenticate(requestDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto responseDto = new LoginResponseDto(jwtToken);

        return ResponseEntity.ok(responseDto);
    }
}
