package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.dtos.CreateUserRequestDto;
import com.coupongenerator.admin.dtos.UserResponseDto;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.exceptions.UserAlreadyExistsException;
import com.coupongenerator.admin.exceptions.UserNotFoundException;
import com.coupongenerator.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequestDto requestDto
    ) throws UserAlreadyExistsException, PlanNotFoundException {
        UserResponseDto responseDto = userService.createUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDto> responseDtoList = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUser(
            @PathVariable String userName
    ) throws UserNotFoundException {
        UserResponseDto responseDto = userService.getUser(userName);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
