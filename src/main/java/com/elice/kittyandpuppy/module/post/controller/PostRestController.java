package com.elice.kittyandpuppy.module.post.controller;


import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.post.dto.RequestPost;
import com.elice.kittyandpuppy.module.post.dto.ResponsePost;
import com.elice.kittyandpuppy.module.post.dto.ResponsePostLike;
import com.elice.kittyandpuppy.module.post.entity.Post;
import com.elice.kittyandpuppy.module.post.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name="커뮤니티 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostRestController {
    private final PostService postService;

    @GetMapping("/communities")
    public ResponseEntity<Page<ResponsePostLike>> getCommunityList(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault(size = 13, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> posts = postService.getCommunityList(search, pageable);
        Page<ResponsePostLike> responsePostLikes = posts.map(post -> {
            int likeCount = postService.getLikeCount(post.getId());
            return new ResponsePostLike(post, likeCount);
        });
        return ResponseEntity.ok().body(responsePostLikes);
    }

    @GetMapping("/community/{id}")
    public ResponseEntity<Map<String, Object>> getCommunityDetail(@PathVariable(name = "id") Long id) {
        Post post = postService.getCommunityDetail(id);
        int likeCount = postService.getLikeCount(id);

        return ResponseEntity.ok().body(Map.of(
                "post", new ResponsePost(post),
                "likeCount", likeCount
        ));
    }

    // POST
    @PostMapping("/community/post")
    public ResponseEntity<Post> createCommunityDetail(@RequestBody RequestPost requestPost) {
        Post created = postService.createCommunityDetail(requestPost);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.CREATED).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PUT, 수정 완료
    @PutMapping("/community/post/{id}")
    public ResponseEntity<Post> updateCommunityDetail(@PathVariable(name = "id") Long id, @RequestBody RequestPost requestPost) {
        Post updated = postService.updateCommunityDetail(id, requestPost);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/community/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        postService.deleteCommunity(id);
        return ResponseEntity.ok().build();
    }

    // 조회수 증가를 위한 컨트롤러
    @PostMapping("/community/{postId}/increment-views")
    public ResponseEntity<Void> incrementPostViews(@PathVariable Long postId) {
        postService.incrementViews(postId);
        return ResponseEntity.ok().build(); // 성공적으로 처리되면 OK 상태 코드 반환
    }

    // 특정 사용자의 좋아요 상태 확인
    @GetMapping("/community/post/{id}/like-status")
    public ResponseEntity<Map<String, Boolean>> getLikeStatus(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        boolean isLiked = postService.isPostLikedByUser(id, member.getId());
        return ResponseEntity.ok(Map.of("isLiked", isLiked));
    }

    // 좋아요 상태 업데이트
    @PostMapping("/community/post/{id}/like")
    public ResponseEntity<Map<String, Object>> toggleLikeStatus(@PathVariable Long id, @AuthenticationPrincipal Member member) {
        Map<String, Object> response = postService.toggleLikeStatus(id, member.getId());
        return ResponseEntity.ok(response);
    }
}
