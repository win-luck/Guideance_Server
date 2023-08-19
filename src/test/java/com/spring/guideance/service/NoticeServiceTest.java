package com.spring.guideance.service;

import com.spring.guideance.user.domain.Notice;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.response.ResponseNoticeDto;
import com.spring.guideance.user.repository.NoticeRepository;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.user.service.NoticeService;
import com.spring.guideance.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

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

}
