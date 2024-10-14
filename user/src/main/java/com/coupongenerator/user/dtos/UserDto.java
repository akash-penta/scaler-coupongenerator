package com.coupongenerator.user.dtos;

import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;

    private Date createdAt;

    private Date modifiedAt;

    private String userName;

    private String businessName;

    private UserStatus status;

    private Date expireDate;

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                user.getUsername(),
                user.getBusinessName(),
                user.getStatus(),
                user.getExpireDate()
        );
    }
}
