package com.elice.kittyandpuppy.module.post.repository;

import com.elice.kittyandpuppy.module.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    void deleteByPostIdAndMemberId(Long postId, Long memberId);
    int countByPostId(Long postId); // 좋아요 개수 세기
}
