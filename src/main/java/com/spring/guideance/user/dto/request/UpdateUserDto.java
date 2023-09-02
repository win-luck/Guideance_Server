package com.spring.guideance.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserDto {

    private String userName;

    public UpdateUserDto(String userName) {
        this.userName = userName;
    }
}
