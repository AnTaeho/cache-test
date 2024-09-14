package com.example.cache_test.post.controller;

import com.example.cache_test.dto.CommonResponse;
import com.example.cache_test.dto.PageInfo;
import com.example.cache_test.post.dto.PostListResponse;
import com.example.cache_test.post.dto.PostSimpleResponse;
import com.example.cache_test.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public CommonResponse<PostListResponse> getAllPost(Pageable pageable) {
        Page<PostSimpleResponse> allPost = postService.findAllPost(pageable);
        return new CommonResponse<>(
                new PostListResponse(allPost.getContent()),
                PageInfo.of(allPost)
        );
    }

}
