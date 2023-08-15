package com.spring.guideance.tag.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "tag") // 이 태그를 구독하는 유저들
    private List<UserTag> userTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag") // 이 태그를 가지고 있는 게시글들
    private List<ArticleTag> articleTags = new ArrayList<>();

    public static Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.tagName = tagName;
        return tag;
    }
}
