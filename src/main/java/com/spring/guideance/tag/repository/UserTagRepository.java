package com.spring.guideance.tag.repository;

import com.spring.guideance.tag.domain.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    Optional<UserTag> findByTagIdAndUserId(Long tagId, Long userId);

    List<UserTag> findByUserId(Long userId);

    List<UserTag> findByTagId(Long tagId);

    boolean existsByTagIdAndUserId(Long tagId, Long userId);

    void deleteByTagIdAndUserId(Long tagId, Long userId);
}
