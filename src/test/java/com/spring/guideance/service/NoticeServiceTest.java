package com.spring.guideance.service;

import com.spring.guideance.user.domain.Notice;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.repository.NoticeRepository;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.user.service.NoticeService;
import com.spring.guideance.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    UserService userService;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserNoticeRepository userNoticeRepository;

    @Test
    public void 특정유저받은알림조회(){
        // given
        String name = "test";
        String email = "1";

        Notice notice = Notice.createNotice(1, "test", "test");
        Long id = noticeRepository.save(notice).getId();
        userService.createUser(new CreateUserDto(name, email));
        User user = userRepository.findByEmail(email).orElse(null);
        assert user != null;
        noticeService.createNotice(id, user.getId());

        // then
        assertEquals(userNoticeRepository.findByUserId(user.getId()).size(), 1);
    }

    @Test
    public void 특정유저가자신의알림삭제(){
        // given
        String name = "test";
        String email = "2";

        Notice notice = Notice.createNotice(1, "test", "test");
        Long id = noticeRepository.save(notice).getId();
        userService.createUser(new CreateUserDto(name, email));
        User user = userRepository.findByEmail(email).orElse(null);
        assert user != null;
        Long userNoticeId = noticeService.createNotice(id, user.getId());

        // when
        noticeService.deleteUserNotice(userNoticeId);

        // then
        assertEquals(userNoticeRepository.findByUserId(user.getId()).size(), 0);
    }

    @Test
    public void 특정알림받은유저조회(){
        Notice notice = Notice.createNotice(1, "test", "test");
        Long id = noticeRepository.save(notice).getId();

        // given
        String name = "test";
        String email = "3";
        userService.createUser(new CreateUserDto(name, email));
        User user = userRepository.findByEmail(email).orElse(null);
        assert user != null;
        noticeService.createNotice(id, user.getId());

        // when
        assertEquals(userNoticeRepository.findByNoticeId(id).size(), 1);
    }
}
