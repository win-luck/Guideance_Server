package com.spring.guideance.service;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.dto.request.CreateArticleDto;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.tag.domain.ArticleTag;
import com.spring.guideance.tag.domain.Tag;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.tag.repository.ArticleTagRepository;
import com.spring.guideance.tag.repository.TagRepository;
import com.spring.guideance.tag.repository.UserTagRepository;
import com.spring.guideance.tag.service.TagService;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.util.exception.TagException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class TagServiceTest {

    @Autowired
    TagService tagService;
    @Autowired
    UserTagRepository userTagRepository;
    @Autowired
    ArticleTagRepository articleTagRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;

    // 태그 생성
    @Test
    public void 태그생성(){
        // given
        String tagName = "tag1";

        // when
        Long tagId = tagService.createTag(tagName);

        // then
        assertEquals(tagName, tagRepository.findById(tagId).get().getTagName());
    }


    // 태그명 중복 검증
    @Test(expected = TagException.class)
    public void 태그명중복검증(){
        // given
        String tagName = "tag1";
        String tagName2 = "tag1";

        // when
        tagService.createTag(tagName);
        tagService.createTag(tagName2);

        // then
        assertEquals(tagName, tagRepository.findByTagName(tagName).get().getTagName());
    }

    // 태그 삭제
    @Test
    public void 태그삭제(){
        // given
        String tagName = "tag1";
        Long tagId = tagService.createTag(tagName);

        // when
        tagService.deleteTag(tagId);

        // then
        assertEquals(0, tagRepository.findAll().size());
    }

    // 태그 구독
    @Test
    public void 태그구독(){
        // given
        String tagName = "tag1";
        Long tagId = tagService.createTag(tagName);
        Long userId = userRepository.save(User.createUser("test", "test")).getId();

        // when
        tagService.subscribeTag(userId, tagId);

        // then
        assertEquals(1, userTagRepository.findAll().size());
    }

    // 태그 구독 취소
    @Test
    public void 태그구독취소(){
        // given
        String tagName = "tag1";
        Long tagId = tagService.createTag(tagName);
        Long userId = userRepository.save(User.createUser("test", "test")).getId();

        // when
        tagService.subscribeTag(userId, tagId);
        tagService.unsubscribeTag(userId, tagId);

        // then
        assertEquals(0, userTagRepository.findAll().size());
    }

    // 태그 목록 조회
    @Test
    public void 태그목록조회(){
        // given
        User user = User.createUser("test", "test");
        Long userId = userRepository.save(user).getId();

        String tagName = "tag1";
        String tagName2 = "tag2";
        String tagName3 = "tag3";
        tagService.createTag(tagName);
        tagService.createTag(tagName2);
        tagService.createTag(tagName3);

        // when
        Page<ResponseTagDto> tags = tagService.getTagList(userId, PageRequest.of(0, 10));

        // then
        assertEquals(3, tags.getTotalElements());
    }

    // 태그 검색
    @Test
    public void 태그검색(){
        // given
        User user = User.createUser("test", "test");
        Long userId = userRepository.save(user).getId();
        String tagName = "tag1";
        String tagName2 = "tag2";
        String tagName3 = "tag3";
        tagService.createTag(tagName);
        tagService.createTag(tagName2);
        tagService.createTag(tagName3);

        // when
        Page<ResponseTagDto> tags = tagService.searchTag(userId, "tag", PageRequest.of(0, 10));

        // then
        assertEquals(3, tags.getTotalElements());
    }

    // 특정 태그가 포함된 게시물 목록 조회
    @Test
    public void 특정태그가포함된게시물목록조회(){
        // given
        User user = User.createUser("test", "test");
        Long userId = userRepository.save(user).getId();
        List<String> tagNames = new ArrayList<>();
        tagNames.add("tag1");
        Article article = Article.createArticle("title", "content");
        article.setUser(user);
        Long articleId = articleRepository.save(article).getId();
        Long tagId = tagRepository.save(Tag.createTag("tag1")).getId();

        Article article1 = articleRepository.findById(articleId).get();
        Tag tag = tagRepository.findById(tagId).get();
        articleTagRepository.save(ArticleTag.createArticleTag(article1, tag));

        // when
        int articleCount = tagService.getArticleListByTag(tagId).size();

        // then
        assertEquals(1, articleCount);
    }

    // 특정 태그를 구독하는 유저 목록 조회
    @Test
    public void 특정태그를구독하는유저목록조회(){
        // given
        User user  = userRepository.save(User.createUser("test", "test"));
        Tag tag = tagRepository.save(Tag.createTag("tag1"));

        // when
        tagService.subscribeTag(user.getId(), tag.getId());
        int userCount = tagService.getUserListByTag(tag.getId()).size();

        // then
        assertEquals(1, userCount);
    }

}
