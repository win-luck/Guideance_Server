package com.spring.guideance;

import com.spring.guideance.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<User, String> {
}
