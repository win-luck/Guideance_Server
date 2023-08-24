package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestLikeDto {

    private Long userId;

    public RequestLikeDto(Long userId) {
        this.userId = userId;
    }
}
