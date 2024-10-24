package com.coupongenerator.user.services;

import com.coupongenerator.user.dtos.LoginRequestDto;
import com.coupongenerator.user.entities.PlanDetails;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.UserStatus;
import com.coupongenerator.user.exceptions.UnauthorizedOperation;
import com.coupongenerator.user.exceptions.UserNotFoundException;
import com.coupongenerator.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authenticate(LoginRequestDto requestDto) throws UserNotFoundException, UnauthorizedOperation {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUserName(),
                        requestDto.getPassword()
                )
        );

        return validateUserExpiration(requestDto.getUserName());
    }

    public User getCurrentUser() throws UserNotFoundException, UnauthorizedOperation {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return validateUserExpiration(userName);
    }

    private User validateUserExpiration(String userName) throws UserNotFoundException, UnauthorizedOperation {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found, Please re-login!");
        }

        User user = optionalUser.get();

        Date currentDate = new Date();

        if(currentDate.after(user.getExpireDate())) {
            PlanDetails nextPlan = user.getNextPlan();
            if(nextPlan != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.MONTH, nextPlan.getMonths());
                Date newExpireDate = calendar.getTime();
                if(currentDate.before(newExpireDate)) {
                    user.setStatus(UserStatus.ACTIVE);
                    user.setExpireDate(newExpireDate);
                    user.setCurrentPlan(nextPlan);
                    user.setNextPlan(null);
                    userRepository.save(user);
                    return user;
                }
            }
            user.setStatus(UserStatus.EXPIRED);
            userRepository.save(user);
            throw new UnauthorizedOperation("Your account got expired, Please contact admin to recharge..!");
        }

        return user;
    }
}
