package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestLikeDto {
    private Long userId;

    public RequestLikeDto(Long userId) {
        this.userId = userId;
    }

}
