package com.spring.guideance.user.dto.response;

import com.spring.guideance.user.domain.User;
import lombok.Data;

@Data
public class ResponseUserDto {

    private Long userId;
    private String name;
    private String keyCode;
    private String profileImage;

    private ResponseUserDto(User user) {
        this.userId = user.getId();
        this.keyCode = user.getKeyCode();
        this.profileImage = user.getProfileImage();
    }

    public static ResponseUserDto from(User user) {
        return new ResponseUserDto(user);
    }
}
