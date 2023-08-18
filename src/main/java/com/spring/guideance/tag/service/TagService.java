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
import com.spring.guideance.util.exception.ResponseCode;
import com.spring.guideance.util.exception.ArticleException;
import com.spring.guideance.util.exception.TagException;
import com.spring.guideance.util.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));

        tagList.forEach(tagName -> {
            // 태그가 존재하지 않으면 새로운 태그를 생성하고, 게시물에 태그를 추가
            if (tagRepository.findByTagName(tagName).isEmpty()) {
                Long tagId = createTag(tagName);
                articleTagRepository.save(ArticleTag.createArticleTag(article, tagRepository.findById(tagId).orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND))));
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
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
        if (userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isPresent())
            throw new TagException(ResponseCode.TAG_ALREADY_SUBSCRIBED);
        userTagRepository.save(UserTag.createUserTag(tag, user));
    }

    // 특정 유저가 태그 구독을 취소
    @Transactional
    public void unsubscribeTag(Long userId, Long tagId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
        if (userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isEmpty())
            throw new TagException(ResponseCode.UNSUBSCRIBE_TAG);
        Long id = userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).get().getId();
        userTagRepository.deleteById(id);
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
                    throw new TagException(ResponseCode.TAG_ALREADY_EXISTS);
                });
    }

    // 태그 삭제
    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
        tagRepository.delete(tag);
    }

    // 태그 목록 조회 (태그명, 게시물수, 좋아요수) (페이징)
    public Page<ResponseTagDto> getTagList(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        List<Tag> tagList = tagRepository.findAll();

        List<ResponseTagDto> dtoList = tagList.stream()
                .map(tag -> {
                    boolean isSubscribed = userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isPresent();
                    int articleCount = tag.getArticleTags().size(); // 게시물 수
                    int likeCount = tag.getTotalLikeCount(); // 좋아요 수

                    ResponseTagDto responseTagDto = new ResponseTagDto(tag.getId(), tag.getTagName(), articleCount, likeCount);
                    responseTagDto.setSubscribed(isSubscribed);
                    return responseTagDto;
                }).sorted((tag1, tag2) -> {
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
                }).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    // 태그 검색 결과 조회 (페이징)
    public Page<ResponseTagDto> searchTag(String tagName, Pageable pageable) {
        Page<Tag> tagList = tagRepository.findByTagNameContaining(tagName, pageable);

        return tagList.map(tag -> {
            int articleCount = tag.getArticleTags().size(); // 게시물 수
            int likeCount = tag.getTotalLikeCount(); // 좋아요 수
            return new ResponseTagDto(tag.getId(), tag.getTagName(), articleCount, likeCount);
        });
    }

    // 특정 태그가 포함된 게시물 목록 조회
    public List<ResponseSimpleArticleDto> getArticleListByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
        List<ArticleTag> articleTagList = articleTagRepository.findByTagId(tag.getId());

        return articleTagList.stream()
                .map(articleTag -> {
                    Article article = articleTag.getArticle();
                    User user = article.getUser();
                    int likeCount = article.getLikes().size(); // 좋아요 수
                    int commentCount = article.getComments().size(); // 댓글 수
                    return new ResponseSimpleArticleDto(article.getId(), article.getTitle(), article.getContents(), user.getName(), likeCount, commentCount);
                })
                .collect(Collectors.toList());
    }


    // 특정 태그를 구독하는 유저 목록 조회
    public List<ResponseUserDto> getUserListByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
        List<UserTag> userTagList = userTagRepository.findByTagId(tag.getId());

        return userTagList.stream()
                .map(userTag -> {
                    User user = userTag.getUser();
                    return new ResponseUserDto(user);
                })
                .collect(Collectors.toList());
    }


}
