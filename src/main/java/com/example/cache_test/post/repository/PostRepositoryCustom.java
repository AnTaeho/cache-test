package com.example.cache_test.post.repository;

import com.example.cache_test.post.dto.PostSimpleResponse;
import com.example.cache_test.dto.RestPage;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    RestPage<PostSimpleResponse> getPostPageWithWriterPage(Pageable pageable);

}
