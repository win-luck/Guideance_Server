package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 게시물 검색 - findByTitleContaining (페이징)
    Page<Article> findAllByTitleContaining(String keyword, Pageable pageable);

    Optional<Article> findById(Long articleId);

    // 최신 게시물이 앞에 오도록 정렬하여 조회 (페이징)
    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Article> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<Article> findAllByLikesUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
