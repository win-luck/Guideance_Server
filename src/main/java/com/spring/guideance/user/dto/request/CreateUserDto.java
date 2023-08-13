package com.spring.guideance.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDto {
    private String name;
    private String email;

    public CreateUserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
