package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByArticleIdAndUserId(Long articleId, Long userId);

    List<Likes> findAllByArticleId(Long articleId);

    List<Likes> findAllByUserId(Long userId);

    void deleteById(Long id);
}
