package com.spring.guideance.post.service;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.domain.Comment;
import com.spring.guideance.post.domain.Likes;
import com.spring.guideance.post.dto.request.*;
import com.spring.guideance.post.dto.response.ResponseArticleDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.post.repository.CommentRepository;
import com.spring.guideance.post.repository.LikesRepository;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.ArticleException;
import com.spring.guideance.util.exception.ResponseCode;
import com.spring.guideance.util.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, ResponseSimpleArticleDto> redisArticleTemplate;

    // 게시물 목록 조회 (제목, 내용, 작성자, 좋아요 수, 댓글 수, 유저의 좋아요누름여부) (페이징) (Redis 캐싱)
    @Transactional(readOnly = true)
    public Page<ResponseSimpleArticleDto> getArticles(Pageable pageable, Long userId) {
        if (!userRepository.existsById(userId))
            throw new UserException(ResponseCode.USER_NOT_FOUND);

        String cacheKey = "articles:page:" + pageable.getPageNumber() + ":size:" + pageable.getPageSize();
        if (Boolean.TRUE.equals(redisArticleTemplate.hasKey(cacheKey))) { // Redis 캐시에 데이터가 있는 경우
            List<ResponseSimpleArticleDto> cachedArticles = redisArticleTemplate.opsForList().range(cacheKey, 0, -1);
            if (cachedArticles != null)
                return new PageImpl<>(cachedArticles, pageable, cachedArticles.size());
        }

        // Redis 캐시에 데이터가 없는 경우, 데이터베이스에서 조회 후 캐시에 저장
        Page<Article> articles = articleRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<ResponseSimpleArticleDto> responseSimpleArticleDtos = articles.map(article -> {
            boolean isLiked = likesRepository.existsByArticleIdAndUserId(article.getId(), userId);
            return ResponseSimpleArticleDto.from(article, isLiked);
        }).getContent();

        // Redis에 데이터를 저장하고 만료 시간 설정
        redisArticleTemplate.opsForList().rightPushAll(cacheKey, responseSimpleArticleDtos.toArray(new ResponseSimpleArticleDto[0]));
        redisArticleTemplate.expire(cacheKey, 5, TimeUnit.MINUTES); // 5분 후 만료
        return new PageImpl<>(responseSimpleArticleDtos, pageable, responseSimpleArticleDtos.size());
    }

    // 특정 게시물 정보 조회 (제목, 내용, 댓글 목록, 좋아요 목록, 작성자, 작성일시)
    @Transactional(readOnly = true)
    public ResponseArticleDto getSingleArticle(Long articleId, Long userId) {
        Article article = getArticleById(articleId);
        // 유저가 이 게시물에 좋아요를 눌렀는지 확인
        return ResponseArticleDto.from(article, likesRepository.existsByArticleIdAndUserId(articleId, userId));
    }

    // 게시물 검색 결과 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<ResponseSimpleArticleDto> searchArticles(String keyword, Pageable pageable, Long userId) {
        return articleRepository.findAllByTitleContaining(keyword, pageable)
                .map(article -> ResponseSimpleArticleDto.from(article, likesRepository.existsByArticleIdAndUserId(article.getId(), userId)));
    }

    // 게시물 작성(태그 관련 로직은 추후 추가)
    @Transactional
    public Long createArticle(CreateArticleDto articleDto) {
        Article article = Article.createArticle(articleDto.getTitle(), articleDto.getContents());
        User user = getUserById(articleDto.getUserId());
        article.setUser(user);
        return articleRepository.save(article).getId();
    }

    // 게시물 수정
    @Transactional
    public void updateArticle(Long articleId, UpdateArticleDto articleDto) {
        Article article = authorCheckAndBringArticle(articleId, articleDto.getUserId());
        // 태그는 일단 수정 불가능하도록 설정
        article.updateArticle(articleDto.getTitle(), articleDto.getContents());
        articleRepository.save(article);
    }

    // 게시물 삭제
    @Transactional
    public void deleteArticle(Long articleId, DeleteArticleDto articleDto) {
        Article article = authorCheckAndBringArticle(articleId, articleDto.getUserId());
        articleRepository.delete(article);
    }

    // 게시물 수정/삭제 요청 시 당사자가 맞는지 체크
    private Article authorCheckAndBringArticle(Long articleId, Long userId) {
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if (!article.isAuthor(user)) throw new ArticleException(ResponseCode.FORBIDDEN);
        return article;
    }

    // 게시물에 댓글 작성
    @Transactional
    public Long createComment(Long articleId, CreateCommentDto commentDto) {
        Article article = getArticleById(articleId);
        User user = getUserById(commentDto.getUserId());
        Comment comment = Comment.createComment(commentDto.getContents(), user, article);
        return commentRepository.save(comment).getId();
    }

    // 게시물에 댓글 수정
    @Transactional
    public void updateComment(Long commentId, UpdateCommentDto commentDto) {
        Comment comment = authorCheckAndBringComment(commentId, commentDto.getUserId());
        comment.updateComment(commentDto.getContents());
        commentRepository.save(comment);
    }

    // 게시물에 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = authorCheckAndBringComment(commentId, userId);
        commentRepository.delete(comment);
    }

    // 댓글 수정/삭제 요청 시 당사자가 맞는지 체크
    private Comment authorCheckAndBringComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ArticleException(ResponseCode.COMMENT_NOT_FOUND));
        User user = getUserById(userId);
        if (!comment.isAuthor(user)) throw new ArticleException(ResponseCode.FORBIDDEN);
        return comment;
    }

    // 게시물에 좋아요
    @Transactional
    public Long createLikes(Long articleId, RequestLikeDto likeDto) {
        Article article = getArticleById(articleId);
        User user = getUserById(likeDto.getUserId());
        if (likesRepository.existsByArticleIdAndUserId(articleId, likeDto.getUserId()))
            throw new ArticleException(ResponseCode.LIKE_ALREADY_EXISTS);
        Likes likes = Likes.createLikes(article, user);
        return likesRepository.save(likes).getId();
    }

    // 게시물에 좋아요 취소
    @Transactional
    public void deleteLikes(Long articleId, RequestLikeDto likeDto) {
        Likes likes = likesRepository.findByArticleIdAndUserId(articleId, likeDto.getUserId()).orElseThrow(() -> new ArticleException(ResponseCode.LIKE_NOT_FOUND));
        likesRepository.delete(likes);
    }

    private Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ArticleException(ResponseCode.USER_NOT_FOUND));
    }
}
