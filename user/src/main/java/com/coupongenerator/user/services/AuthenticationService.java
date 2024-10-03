package com.coupongenerator.user.services;

import com.coupongenerator.user.dtos.LoginRequestDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
