package com.example.cache_test.post.repository;

import static com.example.cache_test.QPost.post;

import com.example.cache_test.post.dto.PostSimpleResponse;
import com.example.cache_test.dto.RestPage;
import com.example.cache_test.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public RestPage<PostSimpleResponse> getPostPageWithWriterPage(Pageable pageable) {
        List<Long> postIds = queryFactory
                .select(post.id)
                .from(post)
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (postIds.isEmpty()) {
            return new RestPage<>(new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(post.id.in(postIds))
                .orderBy(post.id.desc())
                .fetch();

        Long count = queryFactory
                .select(post.count())
                .from(post)
                .fetchOne();

        List<PostSimpleResponse> result = posts.stream()
                .map(this::toSimpleResponse)
                .toList();

        return new RestPage<>(new PageImpl<>(result, pageable, count));
    }

    private PostSimpleResponse toSimpleResponse(Post post) {
        return new PostSimpleResponse(
                post.getId(),
                post.getTitle(),
                post.getThumbnail()
        );
    }

}
