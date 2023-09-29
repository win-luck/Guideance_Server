package com.spring.guideance.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateTagDto {

    private String tagName;

    public CreateTagDto(String tagName) {
        this.tagName = tagName;
    }
}
