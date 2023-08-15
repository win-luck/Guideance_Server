package com.spring.guideance.user.dto.response;

import com.spring.guideance.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseUserDto {
    private Long userId;
    private String name;
    private String email;
    private String profileImage;

    public ResponseUserDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }
}
