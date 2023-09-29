package com.spring.guideance.tag.repository;

import com.spring.guideance.tag.domain.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findByArticleId(Long articleId);

    List<ArticleTag> findByTagId(Long tagId);
}
