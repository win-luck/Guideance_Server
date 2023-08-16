package com.spring.guideance.tag.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTagDto {
    private String tagName;

    public CreateTagDto(String tagName) {
        this.tagName = tagName;
    }
}
