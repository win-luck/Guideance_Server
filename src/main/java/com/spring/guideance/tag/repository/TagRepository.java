package com.spring.guideance.tag.repository;

import com.spring.guideance.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);

    List<Tag> findByTagNameContaining(String tagName);
}
