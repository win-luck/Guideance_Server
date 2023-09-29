package com.spring.guideance.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserDto {

    private String name;
    private String keyCode;
    private String profileImage;

    public CreateUserDto(String name, String keyCode, String profileImage) {
        this.name = name;
        this.keyCode = keyCode;
        this.profileImage = profileImage;
    }
}
