package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByArticleIdAndUserId(Long articleId, Long userId);

    void deleteById(Long id);

    boolean existsByArticleIdAndUserId(Long articleId, Long userId);
}
