package com.spring.guideance.user.service;

import com.spring.guideance.post.dto.response.ResponseCommentDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.request.UpdateUserDto;
import com.spring.guideance.user.dto.response.ResponseNoticeDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.ResponseCode;
import com.spring.guideance.util.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public Long createUser(CreateUserDto createUserDto) {
        ValidateDuplicateUser(createUserDto);
        return userRepository.save(User.createUser(createUserDto)).getId();
    }

    // 중복 이메일 체크
    private void ValidateDuplicateUser(CreateUserDto createUserDto) {
        userRepository.findByEmail(createUserDto.getEmail())
                .ifPresent(user -> {
                    throw new UserException(ResponseCode.USER_ALREADY_EXISTS);
                });
    }

    // 로그인
    @Transactional
    public ResponseUserDto login(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return new ResponseUserDto(user);
    }

    // 회원정보 조회
    public ResponseUserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return new ResponseUserDto(user);
    }

    // 회원정보 수정(이름/프사 변경)
    @Transactional
    public void updateUser(UpdateUserDto updateUserDto, String imageUrl) {
        User user = userRepository.findById(updateUserDto.getUserId()).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        user.updateUser(updateUserDto.getUserName(), imageUrl);
        userRepository.save(user);
    }

    // 회원정보 삭제
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        userRepository.deleteById(user.getId());
    }

    // 유저가 작성한 게시물 조회
    public List<ResponseSimpleArticleDto> getUserArticles(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return user.getArticles().stream()
                .map(ResponseSimpleArticleDto::new)
                .collect(Collectors.toList());
    }

    // 유저가 구독한 태그 조회
    public List<ResponseTagDto> getUserTags(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return user.getUserTags().stream()
                .map(ResponseTagDto::new)
                .collect(Collectors.toList());
    }

    // 유저가 수신한 알림 조회
    public List<ResponseNoticeDto> getUserNotices(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return user.getUserNotices().stream()
                .map(ResponseNoticeDto::new)
                .collect(Collectors.toList());
    }

    // 유저가 좋아요 누른 게시물 조회
    public List<ResponseSimpleArticleDto> getUserLikesArticles(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return user.getLikes().stream()
                .map(articleLike -> new ResponseSimpleArticleDto(
                        articleLike.getArticle().getId(),
                        articleLike.getArticle().getTitle(),
                        articleLike.getArticle().getContents(),
                        articleLike.getArticle().getUser().getName(),
                        articleLike.getArticle().getLikes().size(),
                        articleLike.getArticle().getComments().size()
                ))
                .collect(Collectors.toList());
    }

    // 유저가 작성한 댓글 조회
    public List<ResponseCommentDto> getUserComments(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return user.getComments().stream()
                .map(ResponseCommentDto::new)
                .collect(Collectors.toList());
    }

}
