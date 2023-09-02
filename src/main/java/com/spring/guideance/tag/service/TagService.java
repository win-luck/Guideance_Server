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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final UserTagRepository userTagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final RedisTemplate<String, ResponseTagDto> redisTagTemplate;

    // 클라이언트가 보낸 게시물 생성 요청에서 태그 문자열 리스트를 받아서 순회하면서 새 태그를 생성하고, 게시물에 태그를 추가하는 메서드
    @Transactional
    public void addTagToArticle(Long articleId, List<String> tagList) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ResponseCode.ARTICLE_NOT_FOUND));

        tagList.forEach(tagName -> {
            // 태그가 존재하지 않으면 새로운 태그를 생성하고, 게시물에 태그를 추가
            if (!tagRepository.existsByTagName(tagName)) {
                Long tagId = createTag(tagName);
                articleTagRepository.save(ArticleTag.createArticleTag(article, tagRepository.findById(tagId).orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND))));
            } else {
                // 이미 태그가 존재하면 게시물에 태그를 추가
                Tag tag = tagRepository.findByTagName(tagName).orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
                articleTagRepository.save(ArticleTag.createArticleTag(article, tag));
            }
        });
    }

    // 특정 유저가 태그를 구독
    @Transactional
    public void subscribeTag(Long userId, Long tagId) {
        User user = getUserById(userId);
        Tag tag = getTagById(tagId);
        if (userTagRepository.existsByTagIdAndUserId(tag.getId(), user.getId()))
            throw new TagException(ResponseCode.TAG_ALREADY_SUBSCRIBED);
        userTagRepository.save(UserTag.createUserTag(tag, user));
    }

    // 특정 유저가 태그 구독을 취소
    @Transactional
    public void unsubscribeTag(Long userId, Long tagId) {
        User user = getUserById(userId);
        Tag tag = getTagById(tagId);
        if (!userTagRepository.existsByTagIdAndUserId(tag.getId(), user.getId()))
            throw new TagException(ResponseCode.UNSUBSCRIBE_TAG);
        userTagRepository.deleteByTagIdAndUserId(tag.getId(), user.getId());
    }

    // 새로운 태그 생성(직접 삽입)
    @Transactional
    public Long createTag(String tagName) {
        validateDuplicateTag(tagName);
        return tagRepository.save(Tag.createTag(tagName)).getId();
    }

    // 태그명 중복 검증
    private void validateDuplicateTag(String tagName) {
        if (tagRepository.existsByTagName(tagName))
            throw new TagException(ResponseCode.TAG_ALREADY_EXISTS);
    }

    // 태그 삭제
    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = getTagById(tagId);
        tagRepository.delete(tag);
    }

    // 태그 목록 조회 (태그명, 게시물수, 좋아요수) (페이징) (Redis 캐싱)
    @Transactional(readOnly = true)
    public Page<ResponseTagDto> getTagList(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId))
            throw new UserException(ResponseCode.USER_NOT_FOUND);

        String cacheKey = "tags:page:" + pageable.getPageNumber() + ":size:" + pageable.getPageSize() + ":userId:" + userId;
        if (Boolean.TRUE.equals(redisTagTemplate.hasKey(cacheKey))) { // Redis 캐시에 데이터가 있는 경우
            List<ResponseTagDto> cachedTags = redisTagTemplate.opsForList().range(cacheKey, 0, -1);
            if (cachedTags != null)
                return new PageImpl<>(cachedTags, pageable, cachedTags.size());
        }

        // Redis 캐시에 데이터가 없는 경우, 데이터베이스에서 조회 후 캐시에 저장
        List<ResponseTagDto> dtos = getResponseTagDtoList(userId, tagRepository.findAll());

        // Redis에 데이터를 저장하고 만료 시간 설정
        redisTagTemplate.opsForList().rightPushAll(cacheKey, dtos.toArray(new ResponseTagDto[0]));
        redisTagTemplate.expire(cacheKey, 5, TimeUnit.MINUTES);
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    // 태그 목록을 순회하면서 구독 여부, 게시물 수, 좋아요 수를 조회해서 ResponseTagDto로 변환, 이때 구독 여부에 따라 정렬
    private List<ResponseTagDto> getResponseTagDtoList(Long userId, List<Tag> tags) {
        return tags.stream()
                .map(tag -> { // Tag를 ResponseTagDto로 반환 (구독 여부, 게시물 수, 좋아요 수 포함)
                    boolean isSubscribed = userTagRepository.existsByTagIdAndUserId(tag.getId(), userId);
                    int articleCount = tag.getArticleTags().size();
                    int likeCount = tag.getTotalLikeCount();
                    return ResponseTagDto.of(tag.getId(), tag.getTagName(), articleCount, likeCount, isSubscribed);
                })
                .sorted(Comparator.comparing(ResponseTagDto::isSubscribed).reversed()) // 구독 여부에 따라 정렬
                .collect(Collectors.toList());
    }

    // 태그 검색 결과 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<ResponseTagDto> searchTag(Long userId, String tagName, Pageable pageable) {
        Page<Tag> tagList = tagRepository.findByTagNameContaining(tagName, pageable);

        return tagList.map(tag -> {
            int articleCount = tag.getArticleTags().size(); // 게시물 수
            int likeCount = tag.getTotalLikeCount(); // 좋아요 수
            boolean isSubscribed = userTagRepository.existsByTagIdAndUserId(tag.getId(), userId); // 구독 여부
            return ResponseTagDto.of(tag.getId(), tag.getTagName(), articleCount, likeCount, isSubscribed);
        });
    }

    // 특정 태그가 포함된 게시물 목록 조회
    @Transactional(readOnly = true)
    public List<ResponseSimpleArticleDto> getArticleListByTag(Long tagId) {
        Tag tag = getTagById(tagId);
        List<ArticleTag> articleTagList = articleTagRepository.findByTagId(tag.getId());

        return articleTagList.stream()
                .map(articleTag -> {
                    Article article = articleTag.getArticle();
                    User user = article.getUser();
                    int likeCount = article.getLikes().size(); // 좋아요 수
                    int commentCount = article.getComments().size(); // 댓글 수
                    // 유저의 좋아요 여부
                    boolean isLiked = article.getLikes().stream()
                            .anyMatch(likes -> likes.getUser().getId().equals(user.getId()));
                    return ResponseSimpleArticleDto.of(article.getId(), article.getTitle(), article.getContents(), user.getName(), user.getProfileImage(), likeCount, commentCount, article.getCreatedAt(), isLiked);
                })
                .collect(Collectors.toList());
    }

    // 특정 태그를 구독하는 유저 목록 조회
    @Transactional(readOnly = true)
    public List<ResponseUserDto> getUserListByTag(Long tagId) {
        Tag tag = getTagById(tagId);
        List<UserTag> userTags = userTagRepository.findByTagId(tag.getId());

        return userTags.stream()
                .map(userTag -> {
                    User user = userTag.getUser();
                    return ResponseUserDto.from(user);
                })
                .collect(Collectors.toList());
    }

    private Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(ResponseCode.TAG_NOT_FOUND));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new TagException(ResponseCode.USER_NOT_FOUND));
    }

}
