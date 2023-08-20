package com.spring.guideance.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateUserDto {

    private Long userId;
    private String userName;
    private MultipartFile profileImage;

    public UpdateUserDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
