package com.elice.kittyandpuppy.module.post.controller;


import com.elice.kittyandpuppy.module.post.dto.RequestPost;
import com.elice.kittyandpuppy.module.post.entity.Post;
import com.elice.kittyandpuppy.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    // Community 관련 컨트롤러
    // GET
    @GetMapping("/community")
    public List<Post> getCommunityList() {
        return postService.getCommunityList();
    }

    @GetMapping("/community/{id}")
    public Post getCommunityDetail(@PathVariable Long id) {
        return postService.getCommunityDetail(id);
    }

    // POST
    @PostMapping("/community/post")
    public ResponseEntity<Post> createCommunityDetail(@RequestBody RequestPost requestPost) {
        Post created = postService.createCommunityDetail(requestPost);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PUT, 게시글 수정이 잘 동작하지 않아서 수정이 필요함..
    @PutMapping("/community/post/{id}")
    public ResponseEntity<Post> updateCommunityDetail(@PathVariable Long id, @RequestBody RequestPost requestPost) {
        Post updated = postService.updateCommunityDetail(id, requestPost);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/community/post/{id}")
    public ResponseEntity<Post> delete(@PathVariable Long id) {
        Post deleted = postService.deleteCommunity(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
