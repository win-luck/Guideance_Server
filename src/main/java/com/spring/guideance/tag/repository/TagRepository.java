package com.spring.guideance.tag.repository;

import com.spring.guideance.tag.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);

    Page<Tag> findByTagNameContaining(String tagName, Pageable pageable);
}
