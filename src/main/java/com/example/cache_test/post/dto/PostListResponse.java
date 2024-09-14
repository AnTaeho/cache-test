package com.example.cache_test.post.dto;

import java.util.List;

public record PostListResponse(
        List<PostSimpleResponse> posts
) {
}
