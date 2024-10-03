package com.coupongenerator.admin.services;

import com.coupongenerator.admin.dtos.LoginRequestDto;
import com.coupongenerator.admin.entities.AdminDetails;
import com.coupongenerator.admin.repositories.AdminDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AdminDetailsRepository adminDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AdminDetails signup() {
        AdminDetails adminDetails = new AdminDetails(
                "admin", passwordEncoder.encode("password")
        );

        return adminDetailsRepository.save(adminDetails);
    }

    public AdminDetails authenticate(LoginRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUserName(),
                        requestDto.getPassword()
                )
        );

        return adminDetailsRepository.findByUserName(requestDto.getUserName()).orElseThrow();
    }
}
