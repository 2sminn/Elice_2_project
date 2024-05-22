package com.elice.kittyandpuppy.module.post.service;

import com.elice.kittyandpuppy.module.post.dto.RequestPost;
import com.elice.kittyandpuppy.module.post.dto.ResponsePost;
import com.elice.kittyandpuppy.module.post.entity.Like;
import com.elice.kittyandpuppy.module.post.entity.Post;
import com.elice.kittyandpuppy.module.post.repository.LikeRepository;
import com.elice.kittyandpuppy.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    // Community 관련 서비스
    @Transactional(readOnly = true)
    public Page<Post> getCommunityList(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return postRepository.findByTitleContaining(search, pageable);  // 검색 결과를 반환
        } else {
            return postRepository.findAll(pageable);  // 모든 게시물 반환
        }
    }

    @Transactional(readOnly = true)
    public Post getCommunityDetail(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createCommunityDetail(RequestPost requestPost) {
        Post entity = requestPost.toEntity(requestPost);

        if (entity.getId() != null) {
            return null;
        }
        return postRepository.save(entity);
    }

    public Post updateCommunityDetail(Long id, RequestPost requestPost) {

        // id 로 해당 게시글이 있는지 찾기
        Post entity = postRepository.findById(id).orElse(null);

        // entity 가 있으면 수정하고 저장
        if (entity != null) {
            entity.updatePatch(requestPost.toEntity(requestPost));
            return entity;
        } else {
            return null;
        }
    }

    public void deleteCommunity(Long id) {
        // 대상 찾기
        Post target = postRepository.findById(id).orElse(null);

        // 삭제하고자 하는 엔티티자 존재하면 삭제
        if (target != null) {
            postRepository.delete(target);
        }

    }

    @Transactional
    public Post incrementViews(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없음"));
        post.incrementViewCount();
        return postRepository.save(post);

    }

    // 특정 사용자가 게시글을 좋아요 했는지 확인
    public boolean isPostLikedByUser(Long postId, Long memberId) {
        return likeRepository.existsByPostIdAndMemberId(postId, memberId);
    }

    // 좋아요 개수 세기
    @Transactional(readOnly = true)
    public int getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    // 좋아요 상태 토글 및 좋아요 개수 반환
    public Map<String, Object> toggleLikeStatus(Long postId, Long memberId) {
        boolean isLiked;
        if (isPostLikedByUser(postId, memberId)) {
            likeRepository.deleteByPostIdAndMemberId(postId, memberId);
            isLiked = false;
        } else {
            likeRepository.save(Like.builder()
                    .postId(postId)
                    .memberId(memberId)
                    .build());
            isLiked = true;
        }
        int likeCount = likeRepository.countByPostId(postId);
        return Map.of("isLiked", isLiked, "likeCount", likeCount);
    }
}