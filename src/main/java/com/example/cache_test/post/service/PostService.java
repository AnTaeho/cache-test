package com.example.cache_test.post.service;

import com.example.cache_test.post.dto.PostSimpleResponse;
import com.example.cache_test.dto.RestPage;
import com.example.cache_test.post.repository.PostRepository;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private static final String MAIN_PAGE_CACHE_KEY = "main_page_posts";
    private static final long CACHE_EXPIRATION = 3600;

    private final RedisTemplate<String, Object> redisTemplate;

    public Page<PostSimpleResponse> findAllPost(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        if (pageNumber > 4) {
            return postRepository.getPostPageWithWriterPage(pageable);
        }

        RestPage<PostSimpleResponse> cachedPosts = (RestPage<PostSimpleResponse>) redisTemplate.opsForValue().get(MAIN_PAGE_CACHE_KEY + pageNumber);
        if (cachedPosts != null) {
            log.info("Using cached data page: {}", pageNumber);
            return cachedPosts;
        }

        RestPage<PostSimpleResponse> postPageWithWriterPage = postRepository.getPostPageWithWriterPage(pageable);
        redisTemplate.opsForValue().set(MAIN_PAGE_CACHE_KEY + pageNumber, postPageWithWriterPage, CACHE_EXPIRATION, TimeUnit.SECONDS);
        return postPageWithWriterPage;
    }


}
