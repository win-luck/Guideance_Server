package com.spring.guideance.user.repository;

import com.spring.guideance.user.domain.UserNotice;
import com.spring.guideance.util.SizeUtil;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Long> {

    List<UserNotice> findByNoticeId(Long noticeId);

    @BatchSize(size = SizeUtil.SIZE.SMALL)
    Page<UserNotice> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
