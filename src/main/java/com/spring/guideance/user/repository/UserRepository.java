package com.spring.guideance.user.repository;

import com.spring.guideance.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
