package com.coupongenerator.admin.services;

import com.coupongenerator.admin.dtos.CreateUserRequestDto;
import com.coupongenerator.admin.dtos.UpdateUserRequestDto;
import com.coupongenerator.admin.dtos.UserResponseDto;
import com.coupongenerator.admin.entities.PlanDetails;
import com.coupongenerator.admin.entities.User;
import com.coupongenerator.admin.enums.UserStatus;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.exceptions.UserAlreadyExistsException;
import com.coupongenerator.admin.exceptions.UserNotFoundException;
import com.coupongenerator.admin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanDetailsService planDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
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

    public List<UserResponseDto> getAllUsers() {
        List<User> userList = userRepository.findAll();

        List<UserResponseDto> responseDtoList = new ArrayList<>();

        userList.forEach(user -> responseDtoList.add(UserResponseDto.fromUserEntity(user)));

        return responseDtoList;
    }

    public UserResponseDto getUser(String userName) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(userName);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with user name: " + userName);
        }

        User user = optionalUser.get();

        return UserResponseDto.fromUserEntity(user);
    }

    public void updateUser(
            UUID id,
            UpdateUserRequestDto requestDto
    ) throws UserNotFoundException, UserAlreadyExistsException, PlanNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        User user = optionalUser.get();

        if(requestDto.getUserName() != null && !requestDto.getUserName().isBlank()) {
            Optional<User> optionalUserWithUserName = userRepository.findByUserName(requestDto.getUserName());
            if(optionalUserWithUserName.isPresent()) {
                throw new UserAlreadyExistsException("User already exist with same user name");
            }

            user.setUserName(requestDto.getUserName());
        }

        if(requestDto.getBusinessName() != null && !requestDto.getBusinessName().isBlank()) {
            user.setBusinessName(requestDto.getBusinessName());
        }

        if(requestDto.getStatus() != null && !requestDto.getStatus().isBlank()) {
            user.setStatus(UserStatus.valueOf(requestDto.getStatus()));
        }

        Date currentDate = new Date();

        if(requestDto.getPlanName() != null && !requestDto.getPlanName().isBlank()) {
            PlanDetails planDetails = planDetailsService.getPlanDetailsObject(requestDto.getPlanName());
            user.setCurrentPlan(planDetails);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.MONTH, planDetails.getMonths());
            Date expireDate = calendar.getTime();
            user.setExpireDate(expireDate);
        }

        user.setModifiedAt(currentDate);

        userRepository.save(user);
    }

    public void deleteUser(UUID id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}
