package com.spring.guideance.tag;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    private String tagDescription;

    @OneToMany(mappedBy = "tag") // 이 태그를 구독하는 유저들
    private List<UserTag> userTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag") // 이 태그를 가지고 있는 게시글들
    private List<ArticleTag> articleTags = new ArrayList<>();
}
