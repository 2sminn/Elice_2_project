package com.elice.kittyandpuppy.module.post.service;

import com.elice.kittyandpuppy.module.post.dto.RequestPost;
import com.elice.kittyandpuppy.module.post.entity.Post;
import com.elice.kittyandpuppy.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // Community 관련 서비스
    public List<Post> getCommunityList() {
        return postRepository.findAll();
    }

    public Post getCommunityDetail(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createCommunityDetail(RequestPost requestPost) {
        Post post = requestPost.toEntity();
        if (post.getId() != null) {
            return null;
        }
        return postRepository.save(post);
    }

    public Post updateCommunityDetail(Long id, RequestPost requestPost) {
        Post post = requestPost.toEntity();

        Post target = postRepository.findById(id).orElse(null);

        // 잘못된 요청 처리하기
        if (target == null || id != post.getId()) {
            return null;
        }

        target.updatePatch(post);
        Post updated = postRepository.save(post);
        return updated;
    }

    public Post deleteCommunity(Long id) {
        // 1. 대상 찾기
        Post target = postRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return null;
        }

        // 3. 대상 삭제하기
        postRepository.delete(target);
        return target;
    }
}
