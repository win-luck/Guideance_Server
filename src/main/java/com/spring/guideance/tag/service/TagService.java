package com.spring.guideance.tag.service;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.repository.ArticleRepository;
import com.spring.guideance.tag.domain.ArticleTag;
import com.spring.guideance.tag.domain.Tag;
import com.spring.guideance.tag.domain.UserTag;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.tag.repository.ArticleTagRepository;
import com.spring.guideance.tag.repository.TagRepository;
import com.spring.guideance.tag.repository.UserTagRepository;
import com.spring.guideance.user.domain.User;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final UserTagRepository userTagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    // 클라이언트가 보낸 게시물 생성 요청에서 태그 문자열 리스트를 받아서 순회하면서 새 태그를 생성하고, 게시물에 태그를 추가하는 메서드
    @Transactional
    public void addTagToArticle(Long articleId, List<String> tagList) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));

        tagList.forEach(tagName -> {
            // 태그가 존재하지 않으면 새로운 태그를 생성하고, 게시물에 태그를 추가
            if (tagRepository.findByTagName(tagName).isEmpty()) {
                Long tagId = createTag(tagName);
                articleTagRepository.save(ArticleTag.createArticleTag(article, tagRepository.findById(tagId).orElseThrow(() -> new IllegalStateException("존재하지 않는 태그입니다."))));
            } else {
                // 이미 태그가 존재하면 게시물에 태그를 추가
                Tag tag = tagRepository.findByTagName(tagName).get();
                articleTagRepository.save(ArticleTag.createArticleTag(article, tag));
            }
        });
    }

    // 특정 유저가 태그를 구독
    @Transactional
    public void subscribeTag(Long userId, Long tagId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 태그입니다."));
        if(userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isPresent()) throw new IllegalStateException("이미 구독한 태그입니다.");
        userTagRepository.save(UserTag.createUserTag(tag, user));
    }

    // 특정 유저가 태그 구독을 취소
    @Transactional
    public void unsubscribeTag(Long userId, Long tagId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 태그입니다."));
        if(userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isEmpty()) throw new IllegalStateException("구독하지 않은 태그입니다.");
        userTagRepository.delete(UserTag.createUserTag(tag, user));
    }

    // 새로운 태그 생성(직접 삽입)
    @Transactional
    public Long createTag(String tagName) {
        validateDuplicateTag(tagName);
        return tagRepository.save(Tag.createTag(tagName)).getId();
    }

    // 태그명 중복 검증
    private void validateDuplicateTag(String tagName) {
        tagRepository.findByTagName(tagName)
                .ifPresent(tag -> {
                    throw new IllegalStateException("이미 존재하는 태그입니다.");
                });
    }

    // 태그 삭제
    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 태그입니다."));
        tagRepository.delete(tag);
    }

    // 태그 목록 조회 (태그명, 게시물수, 구독자수) - 유저가 구독한 태그가 앞에 오도록, 가장 최신으로 구독한 태그가 앞에 오도록 정렬
    public List<ResponseTagDto> getTagList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<Tag> tagList = tagRepository.findAll();
        List<ResponseTagDto> responseTagList = new ArrayList<>();

        for (Tag tag : tagList) {
            boolean isSubscribed = userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isPresent();
            int subscribeCount = tag.getUserTags().size(); // 구독자 수
            int likeCount = tag.getArticleTags().size(); // 게시물 수

            ResponseTagDto responseTagDto = new ResponseTagDto(tag.getId(), tag.getTagName(), subscribeCount, likeCount);
            responseTagDto.setSubscribed(isSubscribed);
            responseTagList.add(responseTagDto);
        }

        responseTagList.sort((tag1, tag2) -> {
            if (tag1.isSubscribed() && !tag2.isSubscribed()) return -1;
            else if (!tag1.isSubscribed() && tag2.isSubscribed()) return 1;
            else {
                if (tag1.isSubscribed()) {
                    Instant createdAt1 = Instant.from(userTagRepository.findByTagIdAndUserId(tag1.getTagId(), userId)
                            .orElseThrow(IllegalStateException::new)
                            .getCreatedAt());
                    Instant createdAt2 = Instant.from(userTagRepository.findByTagIdAndUserId(tag2.getTagId(), userId)
                            .orElseThrow(IllegalStateException::new)
                            .getCreatedAt());
                    return createdAt2.compareTo(createdAt1);
                } else {
                    return 0;
                }
            }
        });
        return responseTagList;
    }

    // 태그 검색
    public List<ResponseTagDto> searchTag(String tagName) {
        List<Tag> tagList = tagRepository.findByTagNameContaining(tagName);
        List<ResponseTagDto> responseTagList = new ArrayList<>();

        for (Tag tag : tagList) {
            int subscribeCount = tag.getUserTags().size(); // 구독자 수
            int likeCount = tag.getArticleTags().size(); // 게시물 수

            ResponseTagDto responseTagDto = new ResponseTagDto(tag.getId(), tag.getTagName(), subscribeCount, likeCount);
            responseTagList.add(responseTagDto);
        }

        return responseTagList;
    }

    // 특정 태그가 포함된 게시물 목록 조회
    public List<ResponseSimpleArticleDto> getArticleListByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 태그입니다."));
        List<ArticleTag> articleTagList = articleTagRepository.findByTagId(tag.getId());
        List<ResponseSimpleArticleDto> responseArticleList = new ArrayList<>();

        for (ArticleTag articleTag : articleTagList) {
            Article article = articleTag.getArticle();
            User user = article.getUser();
            int likeCount = article.getArticleTags().size(); // 좋아요 수
            int commentCount = article.getComments().size(); // 댓글 수
            ResponseSimpleArticleDto responseSimpleArticleDto = new ResponseSimpleArticleDto(article.getId(), article.getTitle(), article.getContents(), user.getName(), likeCount, commentCount);
            responseArticleList.add(responseSimpleArticleDto);
        }

        return responseArticleList;
    }


    // 특정 태그를 구독하는 유저 목록 조회
    public List<ResponseUserDto> getUserListByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 태그입니다."));
        List<UserTag> userTagList = userTagRepository.findByTagId(tag.getId());
        List<ResponseUserDto> responseUserList = new ArrayList<>();

        for (UserTag userTag : userTagList) {
            User user = userTag.getUser();
            ResponseUserDto responseUserDto = new ResponseUserDto(user);
            responseUserList.add(responseUserDto);
        }

        return responseUserList;
    }


}
