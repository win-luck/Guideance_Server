package com.spring.guideance.service;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.domain.Likes;
import com.spring.guideance.post.dto.request.*;
import com.spring.guideance.post.dto.response.ResponseArticleDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.post.repository.CommentRepository;
import com.spring.guideance.post.repository.LikesRepository;
import com.spring.guideance.post.service.ArticleService;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/*@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init(){
        articleRepository.deleteAll();
        commentRepository.deleteAll();
        likesRepository.deleteAll();
        userRepository.deleteAll();

        // given
        User user = userRepository.save(User.createUser("test", "test", null));
        Article article = Article.createArticle( "test", "test");
        article.setUser(user);
        articleRepository.save(article);
    }

    @Test
    public void 게시물목록조회(){
        // given
        Long userId = userRepository.findAll().get(0).getId();

        // when
        Page<ResponseSimpleArticleDto> responseArticleDtoList = articleService.getArticles(PageRequest.of(0, 10), userId);

        // then
        assertEquals( "test", responseArticleDtoList.getContent().get(0).getTitle());
    }

    @Test
    public void 게시물생성및상세조회(){
        // given
        Long userId = userRepository.findAll().get(0).getId();
        Long articleId = articleRepository.findAll().get(0).getId();

        // when
        ResponseArticleDto responseArticleDto = articleService.getSingleArticle(articleId, userId);

        // then
        assertEquals(responseArticleDto.getTitle(), "test");
    }

    @Test
    public void 게시물수정(){
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        articleService.updateArticle(articleId, new UpdateArticleDto(userId, "test2", "test2"));

        // then
        assertEquals("test2", articleRepository.findById(articleId).get().getTitle());
    }

    @Test
    public void 게시물삭제(){
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        articleService.deleteArticle(articleId, new DeleteArticleDto(userId, articleId));

        // then
        assertEquals(0, articleRepository.findAll().size());
    }

    @Test
    public void 게시물에댓글작성(){
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        Long commentId = articleService.createComment(articleId, new CreateCommentDto(userId,"test2"));

        // then
        assertEquals(commentRepository.findById(commentId).get().getContents(), "test2");
    }

    @Test
    public void 게시물에댓글수정(){
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        Long commentId = articleService.createComment(articleId, new CreateCommentDto(userId,"test2"));
        articleService.updateComment(commentId, new UpdateCommentDto(userId, "test3"));

        // then
        assertEquals(commentRepository.findById(commentId).get().getContents(), "test3");
    }

    @Test
    public void 게시물에댓글삭제(){
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        Long commentId = articleService.createComment(articleId, new CreateCommentDto(userId,"test2"));
        articleService.deleteComment(commentId, userId);

        // then
        assertNull(commentRepository.findById(commentId).orElse(null));
    }

    @Test
    public void 게시물에좋아요(){
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        Long likeId = articleService.createLikes(articleId, new RequestLikeDto(userId));
        Article article1 = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        Likes likes = likesRepository.findById(likeId).orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다."));
        User user1 = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        // then
        assertEquals(likes.getUser(), user1);
        assertEquals(likes.getArticle(), article1);
        assertEquals(likes, likesRepository.findByArticleIdAndUserId(articleId, userId).get());
    }

    @Test
    public void 게시물에좋아요취소() {
        // given
        Long articleId = articleRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        // when
        Long likeId = articleService.createLikes(articleId, new RequestLikeDto(userId));
        articleService.deleteLikes(articleId, new RequestLikeDto(userId));

        // then
        assertNull(likesRepository.findById(likeId).orElse(null));
    }
}*/
