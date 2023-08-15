package com.spring.guideance.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDto {
    private String email;

    public LoginUserDto(String email) {
        this.email = email;
    }
}
