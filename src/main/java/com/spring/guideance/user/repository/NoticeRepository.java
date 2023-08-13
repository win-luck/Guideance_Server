package com.spring.guideance.user.repository;

import com.spring.guideance.user.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
