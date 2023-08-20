package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Comment;
import com.spring.guideance.util.SizeType;
import org.hibernate.annotations.BatchSize;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @BatchSize(size = SizeType.SIZE.SMALL)
    Page<Comment> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
