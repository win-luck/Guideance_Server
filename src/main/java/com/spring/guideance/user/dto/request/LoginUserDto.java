package com.spring.guideance.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDto {

    private String keyCode;

    public LoginUserDto(String email) {
        this.keyCode = keyCode;
    }
}
