package com.spring.guideance.user.service;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.domain.Comment;
import com.spring.guideance.post.domain.Likes;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.post.repository.CommentRepository;
import com.spring.guideance.post.repository.LikesRepository;
import com.spring.guideance.tag.domain.ArticleTag;
import com.spring.guideance.tag.domain.Tag;
import com.spring.guideance.tag.domain.UserTag;
import com.spring.guideance.user.domain.Notice;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.domain.UserNotice;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.NoticeRepository;
import com.spring.guideance.user.repository.UserNoticeRepository;
import com.spring.guideance.util.exception.ArticleException;
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

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final NoticeRepository noticeRepository;
    private final UserNoticeRepository userNoticeRepository;

    /**
     * 알림 생성+전송 기능
     */

    // 특정 게시물의 주인 user에게 누군가 좋아요를 눌렀음을 알림
    // ???님이 내 게시물에 좋아요를 눌렀습니다.
    // [게시물 제목]
    @Transactional
    public void sendNoticeForLike(Long likesId) {
        Likes likes = likesRepository.findById(likesId).orElseThrow(() -> new ArticleException(ResponseCode.LIKE_NOT_FOUND));
        User sender = likes.getUser();
        User writer = likes.getArticle().getUser();
        if(sender.getId().equals(writer.getId())) return; // 자신이 작성한 게시물에 자신이 좋아요를 눌렀을 경우 알림 생략
        Notice notice = noticeRepository.save(Notice.createNotice(1, sender.getName(), likes.getArticle().getTitle()));
        userNoticeRepository.save(UserNotice.createUserNotice(notice, writer));
    }

    // 특정 게시물의 주인 user에게 누군가 댓글을 달았음을 알림
    // ???님이 내 게시물에 댓글을 남겼습니다.
    // [댓글 내용]
    @Transactional
    public void sendNoticeForComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ArticleException(ResponseCode.COMMENT_NOT_FOUND));
        User sender = comment.getUser();
        User writer = comment.getArticle().getUser();
        if(sender.getId().equals(writer.getId())) return; // 자신이 작성한 게시물에 자신이 댓글을 달았을 경우 알림 전송 생략
        Notice notice = noticeRepository.save(Notice.createNotice(2, sender.getName(), comment.getContents()));
        userNoticeRepository.save(UserNotice.createUserNotice(notice, writer));
    }

    // 한 게시물의 태그를 순회하며 이를 구독 중인 다수 user들에게 새 게시물이 추가되었음을 알림
    // 구독 중인 [???] 태그에 새 게시물이 추가되었습니다.
    // [게시물 제목]
    @Transactional
    public void sendNoticeForNewArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));
        List<Tag> tagList = article.getArticleTags().stream().map(ArticleTag::getTag).collect(Collectors.toList());

        for (Tag tag : tagList) {
            List<User> userList = tag.getUserTags().stream().map(UserTag::getUser).collect(Collectors.toList());

            Notice notice = noticeRepository.save(Notice.createNotice(3, null, article.getTitle()));
            for (User user : userList) {
                if(user.getId().equals(article.getUser().getId())) continue; // 자신이 구독하는 태그를 자신의 게시물에 달아 업로드한 경우 알림 전송 생략
                userNoticeRepository.save(UserNotice.createUserNotice(notice, user));
            }
        }
    }

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
