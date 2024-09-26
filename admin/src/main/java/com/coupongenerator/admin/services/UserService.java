package com.coupongenerator.admin.services;

import com.coupongenerator.admin.dtos.CreateUserRequestDto;
import com.coupongenerator.admin.dtos.UserResponseDto;
import com.coupongenerator.admin.entities.PlanDetails;
import com.coupongenerator.admin.entities.User;
import com.coupongenerator.admin.enums.UserStatus;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.exceptions.UserAlreadyExistsException;
import com.coupongenerator.admin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanDetailsService planDetailsService;

    public UserResponseDto createUser(
            CreateUserRequestDto requestDto
    ) throws UserAlreadyExistsException, PlanNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(requestDto.getUserName());

        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exist with same user name");
        }

        User user = new User();

        user.setUserName(requestDto.getUserName());
        user.setBusinessName(requestDto.getBusinessName());
        user.setPassword(requestDto.getPassword());
        user.setStatus(UserStatus.ACTIVE);

        PlanDetails planDetails = planDetailsService.getPlanDetailsObject(requestDto.getPlanName());
        user.setCurrentPlan(planDetails);

        Date currentDate = new Date();
        user.setCreatedAt(currentDate);
        user.setModifiedAt(currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, planDetails.getMonths());
        Date expireDate = calendar.getTime();
        user.setExpireDate(expireDate);

        userRepository.save(user);

        return UserResponseDto.fromUserEntity(user);
    }
}
