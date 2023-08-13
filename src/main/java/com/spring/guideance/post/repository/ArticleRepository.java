package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 게시물 작성

    // 게시물 수정

    // 게시물 삭제

    // 특정 게시물 조회

    // 게시물 목록 조회
}
