package com.spring.guideance.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserDto {

    private Long userId;
    private String userName;

    public UpdateUserDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
