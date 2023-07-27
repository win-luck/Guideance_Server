package com.spring.guideance.tag;

import com.spring.guideance.article.Article;
import com.spring.guideance.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ArticleTag { // article과 tag의 다대다 관계로 인해 생긴 중간 테이블, 게시물이 담고 있는 태그를 관리
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article; // 게시물이 담고 있는 태그에서 "게시물"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag; // 게시물이 담고 있는 태그에서 "태그"
}
