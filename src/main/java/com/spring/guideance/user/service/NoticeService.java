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

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserNoticeRepository userNoticeRepository;

    // 알림 생성
    @Transactional
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
    @Transactional
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

    /**
     * 아래 메서드는 Controller에서 2개 이상의 Service를 호출해야 하는 경우
     * (예를 들어, Controller에서 좋아요 API가 실행되면 좋아요를 DB에 좋아요를 반영하기 위해 ArticleService가 호출되고,
     * 이후 유저에게 좋아요 알림을 보내야 하므로 NoticeService가 호출되어야 함)
     * 에 대응하는 복합적인 메서드이므로 Controller 파일 생성 후 전반적인 구도가 잡힌 뒤 구현 예정
     */

    // 특정 게시물의 주인 user에게 누군가 좋아요를 눌렀음을 알림

    // 특정 게시물의 주인 user에게 누군가 댓글을 달았음을 알림

    // 특정 태그를 구독 중인 user들에게 새 게시물이 추가되었음을 알림을

}
