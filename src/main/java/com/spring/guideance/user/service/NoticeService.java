package com.spring.guideance.user.service;

import com.spring.guideance.user.domain.UserNotice;
import com.spring.guideance.user.dto.response.ResponseNoticeDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.NoticeRepository;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.NoticeException;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserNoticeRepository userNoticeRepository;

    // 알림 생성
    public Long createNotice(Long noticeId, Long userId) {
        noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeException(ResponseCode.NOTICE_NOT_FOUND));
        return userNoticeRepository.save(UserNotice.createUserNotice(noticeRepository.findById(noticeId).get(), userRepository.findById(userId).get())).getId();
    }

    // 특정 user가 받은 모든 알림 조회
    public List<ResponseNoticeDto> getUserNotice(Long userId) {
        List<UserNotice> userNoticeList = userNoticeRepository.findByUserId(userId);
        return userNoticeList.stream().map(ResponseNoticeDto::new).collect(Collectors.toList());
    }

    // 특정 user가 받은 특정 알림 삭제
    public void deleteUserNotice(Long userNoticeId) {
        userNoticeRepository.deleteById(userNoticeId);
    }

    // 특정 user가 받은 특정 알림을 읽음
    public void readUserNotice(Long userNoticeId) {
        UserNotice userNotice = userNoticeRepository.findById(userNoticeId).orElseThrow(() -> new NoticeException(ResponseCode.NOTICE_NOT_FOUND));
        userNotice.read();
    }

    // 특정 알림을 받은 모든 user 조회
    public List<ResponseUserDto> getUserNoticeUser(Long noticeId) {
        List<UserNotice> userNoticeList = userNoticeRepository.findByNoticeId(noticeId);
        return userNoticeList.stream().map(userNotice -> new ResponseUserDto(userNotice.getUser())).collect(Collectors.toList());
    }

    // 특정 게시물의 주인 user에게 알림을 생성하여 보냄(좋아요 또는 댓글)

    // 특정 태그를 구독하는 user들에게 알림을 생성하여 보냄(태그가 붙은 새 게시물)
}
