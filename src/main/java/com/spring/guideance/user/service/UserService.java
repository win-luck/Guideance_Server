package com.spring.guideance.user.service;

import com.spring.guideance.post.dto.response.ResponseCommentDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.post.repository.CommentRepository;
import com.spring.guideance.post.repository.LikesRepository;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.request.UpdateUserDto;
import com.spring.guideance.user.dto.response.ResponseNoticeDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.ResponseCode;
import com.spring.guideance.util.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserNoticeRepository userNoticeRepository;
    private final LikesRepository likesRepository;

    // 회원가입
    @Transactional
    public ResponseUserDto createUser(CreateUserDto createUserDto) {
        ValidateDuplicateUser(createUserDto);
        User user = userRepository.save(User.createUser(createUserDto.getName(), createUserDto.getKeyCode(), createUserDto.getProfileImage()));
        return ResponseUserDto.from(user);
    }

    // 중복 KeyCode 체크
    private void ValidateDuplicateUser(CreateUserDto createUserDto) {
        if(userRepository.existsByKeyCode(createUserDto.getKeyCode())) {
            throw new UserException(ResponseCode.USER_ALREADY_EXISTS);
        }
    }

    // 로그인
    @Transactional
    public ResponseUserDto login(String key) {
        User user = userRepository.findByKeyCode(key).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return ResponseUserDto.from(user);
    }

    // 회원정보 조회
    @Transactional(readOnly = true)
    public ResponseUserDto getUser(Long userId) {
        User user = getUserById(userId);
        return ResponseUserDto.from(user);
    }

    // 회원 중복 체크
    @Transactional(readOnly = true)
    public boolean isAlreadyUser(String keyCode) {
        return userRepository.existsByKeyCode(keyCode);
    }

    // 회원정보 조회 (유저별 키코드)
    @Transactional(readOnly = true)
    public ResponseUserDto getUserByKeyCode(String keyCode) {
        return ResponseUserDto.from(userRepository.findByKeyCode(keyCode).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND)));
    }

    // 회원정보 수정(이름/프사 변경)
    @Transactional
    public void updateUser(UpdateUserDto updateUserDto, String imageUrl) {
        User user = getUserById(updateUserDto.getUserId());
        user.updateUser(updateUserDto.getUserName(), imageUrl);
        userRepository.save(user);
    }

    // 회원정보 삭제
    @Transactional
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.deleteById(user.getId());
    }

    // 유저가 작성한 게시물 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<ResponseSimpleArticleDto> getUserArticles(Long userId, Pageable pageable) {
        if (!isUserExists(userId)) throw new UserException(ResponseCode.USER_NOT_FOUND);
        return articleRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(article -> ResponseSimpleArticleDto.from(article, likesRepository.existsByArticleIdAndUserId(article.getId(), userId)));
    }

    // 유저가 구독한 태그 조회
    @Transactional(readOnly = true)
    public List<ResponseTagDto> getUserTags(Long userId) {
        User user = getUserById(userId);
        return user.getUserTags().stream()
                .map(ResponseTagDto::from)
                .collect(Collectors.toList());
    }

    // 유저가 수신한 알림 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<ResponseNoticeDto> getUserNotices(Long userId, Pageable pageable) {
        if (isUserExists(userId)) throw new UserException(ResponseCode.USER_NOT_FOUND);
        return userNoticeRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(ResponseNoticeDto::from);
    }

    // 유저가 좋아요 누른 게시물 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<ResponseSimpleArticleDto> getUserLikesArticles(Long userId, Pageable pageable) {
        if (isUserExists(userId)) throw new UserException(ResponseCode.USER_NOT_FOUND);
        return articleRepository.findAllByLikesUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(article -> ResponseSimpleArticleDto.from(article, likesRepository.existsByArticleIdAndUserId(article.getId(), userId)));
    }

    // 유저가 작성한 댓글 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<ResponseCommentDto> getUserComments(Long userId, Pageable pageable) {
        if (isUserExists(userId)) throw new UserException(ResponseCode.USER_NOT_FOUND);
        return commentRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(ResponseCommentDto::from);
    }

    private boolean isUserExists(Long userId) {
        return userRepository.existsById(userId);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
    }
}
