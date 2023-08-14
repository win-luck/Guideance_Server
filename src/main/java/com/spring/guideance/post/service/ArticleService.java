package com.spring.guideance.post.service;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.domain.Comment;
import com.spring.guideance.post.domain.Likes;
import com.spring.guideance.post.dto.request.*;
import com.spring.guideance.post.dto.response.ResponseLikeDto;
import com.spring.guideance.post.dto.response.ResponseArticleDto;
import com.spring.guideance.post.dto.response.ResponseCommentDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.post.repository.CommentRepository;
import com.spring.guideance.post.repository.LikesRepository;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.ArticleException;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    // 게시물 목록 조회 (제목, 내용, 작성자, 좋아요 수, 댓글 수)
    public List<ResponseSimpleArticleDto> getArticles() {
        return articleRepository.findAll().stream()
                .map(article -> new ResponseSimpleArticleDto(
                        article.getTitle(),
                        article.getContents(),
                        article.getUser().getName(),
                        article.getLikes().size(),
                        article.getComments().size()
                ))
                .collect(Collectors.toList());
    }

    // 특정 게시물 정보 조회 (제목, 내용, 댓글 목록, 좋아요 목록, 작성자, 작성일시)
    public ResponseArticleDto getSingleArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));
        return new ResponseArticleDto(
                article.getTitle(),
                article.getContents(),
                article.getUser().getName(),
                article.getLikes().stream()
                        .map(likes -> new ResponseLikeDto(likes.getUser().getId(), likes.getArticle().getId(), likes.getCreatedAt()))
                        .collect(Collectors.toList()),
                article.getComments().stream()
                        .map(comment -> new ResponseCommentDto(
                                comment.getUser().getName(),
                                comment.getContents(),
                                comment.getCreatedAt()
                        ))
                        .collect(Collectors.toList()),
                article.getCreatedAt()
        );
    }

    // 게시물 검색
    public List<ResponseSimpleArticleDto> searchArticles(String keyword) {
        return articleRepository.findAllByTitleContaining(keyword).stream()
                .map(article -> new ResponseSimpleArticleDto(
                        article.getTitle(),
                        article.getContents(),
                        article.getUser().getName(),
                        article.getLikes().size(),
                        article.getComments().size()
                ))
                .collect(Collectors.toList());
    }

    // 게시물 작성(태그 관련 로직은 추후 추가)
    @Transactional
    public Long createArticle(CreateArticleDto articleDto) {
        Article article = Article.createArticle(articleDto);
        User user = userRepository.findById(articleDto.getUserId()).orElseThrow(() -> new ArticleException(ResponseCode.USER_NOT_FOUND));
        article.setUser(user);

        /**
         * 태그 관련 로직은 추후 추가
         */
        return articleRepository.save(article).getId();
    }

    // 게시물 수정
    @Transactional
    public void updateArticle(Long articleId, UpdateArticleDto articleDto) {
        Article article = articleAuthorCheck(articleId, articleDto.getUserId());
        // 태그는 일단 수정 불가능하도록 설정
        article.updateArticle(articleDto);
        articleRepository.save(article);
    }

    // 게시물 삭제
    @Transactional
    public void deleteArticle(Long articleId, DeleteArticleDto articleDto) {
        Article article = articleAuthorCheck(articleId, articleDto.getUserId());
        articleRepository.delete(article);
    }

    // 게시물 수정/삭제 요청 시 당사자가 맞는지 체크
    private Article articleAuthorCheck(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new ArticleException(ResponseCode.USER_NOT_FOUND));
        if (!article.isAuthor(user)) throw new ArticleException(ResponseCode.METHOD_NOT_ALLOWED);
        return article;
    }

    // 게시물에 댓글 작성
    @Transactional
    public Long createComment(Long articleId, CreateCommentDto commentDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new ArticleException(ResponseCode.USER_NOT_FOUND));
        Comment comment = Comment.createComment(commentDto.getContents(), user, article);
        return commentRepository.save(comment).getId();
    }

    // 게시물에 댓글 수정
    @Transactional
    public void updateComment(UpdateCommentDto commentDto) {
        Comment comment = commentAuthorCheck(commentDto.getCommentId(), commentDto.getUserId());
        comment.updateComment(commentDto.getContents());
        commentRepository.save(comment);
    }

    // 게시물에 댓글 삭제
    @Transactional
    public void deleteComment(DeleteCommentDto commentDto) {
        Comment comment = commentAuthorCheck(commentDto.getCommentId(), commentDto.getUserId());
        commentRepository.delete(comment);
    }

    // 댓글 수정/삭제 요청 시 당사자가 맞는지 체크
    private Comment commentAuthorCheck(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ArticleException(ResponseCode.COMMENT_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new ArticleException(ResponseCode.USER_NOT_FOUND));
        if (!comment.isAuthor(user)) {
            throw new ArticleException(ResponseCode.METHOD_NOT_ALLOWED);
        }
        return comment;
    }

    // 게시물에 좋아요
    @Transactional
    public Long createLikes(Long articleId, RequestLikeDto likeDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));
        User user = userRepository.findById(likeDto.getUserId()).orElseThrow(() -> new ArticleException(ResponseCode.USER_NOT_FOUND));
        Likes likes = Likes.createLikes(article, user);
        return likesRepository.save(likes).getId();
    }

    // 게시물에 좋아요 취소
    @Transactional
    public void deleteLikes(Long articleId, RequestLikeDto likeDto) {
        Likes likes = likesRepository.findByArticleIdAndUserId(articleId, likeDto.getUserId()).orElseThrow(() -> new ArticleException(ResponseCode.LIKE_NOT_FOUND));
        likesRepository.delete(likes);
    }

}
