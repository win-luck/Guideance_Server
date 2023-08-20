package com.spring.guideance.user.repository;

import com.spring.guideance.user.domain.UserNotice;
import com.spring.guideance.util.SizeType;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Long> {
    List<UserNotice> findByUserId(Long userId);

    List<UserNotice> findByNoticeId(Long noticeId);

    @BatchSize(size = SizeType.SIZE.SMALL)
    Page<UserNotice> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
