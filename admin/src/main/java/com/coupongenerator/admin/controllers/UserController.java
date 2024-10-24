package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.dtos.CreateUserRequestDto;
import com.coupongenerator.admin.dtos.ExceptionDto;
import com.coupongenerator.admin.dtos.UpdateUserRequestDto;
import com.coupongenerator.admin.dtos.UserResponseDto;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.exceptions.UserAlreadyExistsException;
import com.coupongenerator.admin.exceptions.UserNotFoundException;
import com.coupongenerator.admin.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody CreateUserRequestDto requestDto,
            BindingResult bindingResult
    ) throws UserAlreadyExistsException, PlanNotFoundException {
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

            if(errorSet.contains("Business name is mandatory")) {
                errorMap.put("businessName", "Business name is mandatory");
            }
            else if(errorSet.contains("Business name should not be blank")) {
                errorMap.put("businessName", "Business name should not be blank");
            }

            if(errorSet.contains("Plan name is mandatory")) {
                errorMap.put("planName", "Plan name is mandatory");
            }
            else if(errorSet.contains("Plan name should not be blank")) {
                errorMap.put("planName", "Plan name should not be blank");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }
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
        if(userName.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "User Name should not be blank")
            );
        }
        UserResponseDto responseDto = userService.getUser(userName);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequestDto requestDto
    ) throws UserNotFoundException, UserAlreadyExistsException, PlanNotFoundException {
        if(id.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Id should not be blank")
            );
        }
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }
        userService.updateUser(uuid, requestDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable String id
    ) throws UserNotFoundException {
        if(id.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Id should not be blank")
            );
        }
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }
        userService.deleteUser(uuid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
