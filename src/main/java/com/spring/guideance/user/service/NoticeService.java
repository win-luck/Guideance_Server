package com.spring.guideance.user.service;

import com.spring.guideance.user.domain.Notice;
import com.spring.guideance.user.domain.UserNotice;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.NoticeRepository;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.NoticeException;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserNoticeRepository userNoticeRepository;


    /**
     * 알림 생성+전송 기능
     */

    // 특정 게시물의 주인 user에게 누군가 좋아요를 눌렀음을 알림

    // 특정 게시물의 주인 user에게 누군가 댓글을 달았음을 알림

    // 특정 태그를 구독 중인 user들에게 새 게시물이 추가되었음을 알림

    /**
     * 알림 조회/삭제/읽음 관련 기능
     */

    // 특정 user가 받은 특정 알림 삭제 -> UserController에서 사용
    @Transactional
    public void deleteUserNotice(Long userNoticeId) {
        userNoticeRepository.deleteById(userNoticeId);
    }

    // 특정 user가 받은 특정 알림을 읽음 -> UserController에서 사용
    @Transactional
    public void readUserNotice(Long userNoticeId) {
        UserNotice userNotice = userNoticeRepository.findById(userNoticeId).orElseThrow(() -> new NoticeException(ResponseCode.NOTICE_NOT_FOUND));
        userNotice.read();
    }

    // 전체 알림 조회 - 관리자 기능
    public List<Notice> getNoticeList() {
        return noticeRepository.findAll();
    }

    // 특정 알림을 받은 모든 user 조회 - 관리자 기능
    public List<ResponseUserDto> getUserNoticeUser(Long noticeId) {
        List<UserNotice> userNoticeList = userNoticeRepository.findByNoticeId(noticeId);
        return userNoticeList.stream().map(userNotice -> new ResponseUserDto(userNotice.getUser())).collect(Collectors.toList());
    }

}
