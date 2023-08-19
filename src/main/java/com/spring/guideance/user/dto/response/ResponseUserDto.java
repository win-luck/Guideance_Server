package com.spring.guideance.user.dto.response;

import com.spring.guideance.user.domain.User;
import lombok.Data;

@Data
public class ResponseUserDto {

    private Long userId;
    private String name;
    private String email;
    private String profileImage;

    private ResponseUserDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }

    public static ResponseUserDto from(User user) {
        return new ResponseUserDto(user);
    }
}
