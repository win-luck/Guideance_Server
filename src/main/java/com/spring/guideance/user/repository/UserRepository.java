package com.spring.guideance.user.repository;

import com.spring.guideance.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKeyCode(String key);

    boolean existsByKeyCode(String key);
}
