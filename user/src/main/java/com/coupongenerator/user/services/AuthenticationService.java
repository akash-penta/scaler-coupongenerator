package com.coupongenerator.user.services;

import com.coupongenerator.user.dtos.LoginRequestDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.UserNotFoundException;
import com.coupongenerator.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authenticate(LoginRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUserName(),
                        requestDto.getPassword()
                )
        );

        return userRepository.findByUserName(requestDto.getUserName()).orElseThrow();
    }

    public User getCurrentUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found, Please re-login!");
        }

        return optionalUser.get();
    }
}
