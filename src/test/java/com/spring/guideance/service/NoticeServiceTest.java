package com.spring.guideance.service;

import com.spring.guideance.user.repository.NoticeRepository;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.user.service.NoticeService;
import com.spring.guideance.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeServiceTest {

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

}
