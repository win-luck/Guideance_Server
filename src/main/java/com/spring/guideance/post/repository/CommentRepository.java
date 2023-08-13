package com.spring.guideance.post.repository;

import com.spring.guideance.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
