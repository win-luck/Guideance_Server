package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 태그로 게시물 검색해야하는 로직 필요

    // 게시물 검색 - findByTitleContaining
    List<Article> findAllByTitleContaining(String keyword);

    Optional<Article> findById(Long articleId);
}
