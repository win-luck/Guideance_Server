package com.spring.guideance.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserDto {

    private String keyCode;

    public LoginUserDto(String email) {
        this.keyCode = keyCode;
    }
}
