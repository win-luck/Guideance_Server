package com.spring.guideance;

import com.spring.guideance.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRedisRepositoryTest {

    @Autowired
    private UserRedisRepository userRedisRepository;

    /*@Test
    void test(){
        User user = new User("", "www.usa.com", "1234");
        userRedisRepository.save(user);
        userRedisRepository.findById("1").ifPresent(System.out::println);
        userRedisRepository.count();
    }*/
}
