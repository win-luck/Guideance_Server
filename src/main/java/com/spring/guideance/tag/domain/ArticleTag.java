package com.spring.guideance.tag.domain;

import com.spring.guideance.post.domain.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleTag { // article과 tag의 다대다 관계로 인해 생긴 중간 테이블, 게시물이 담고 있는 태그를 관리

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article; // 게시물이 담고 있는 태그에서 "게시물"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag; // 게시물이 담고 있는 태그에서 "태그"

    public static ArticleTag createArticleTag(Article article, Tag tag) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.article = article;
        articleTag.tag = tag;
        return articleTag;
    }
}
