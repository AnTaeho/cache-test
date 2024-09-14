package com.example.cache_test.post.dto;

public record PostSimpleResponse(
        Long postId,
        String title,
        String thumbnail
) {
}
