package com.elice.kittyandpuppy.module.post.controller;


import com.elice.kittyandpuppy.module.post.dto.RequestPost;
import com.elice.kittyandpuppy.module.post.dto.ResponsePost;
import com.elice.kittyandpuppy.module.post.entity.Post;
import com.elice.kittyandpuppy.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    // Community 관련 컨트롤러
    // GET
    @GetMapping("/communities")
    public ResponseEntity<List<ResponsePost>> getCommunityList() {
        List<ResponsePost> responsePostList = postService.getCommunityList()
                .stream()
                .map(ResponsePost::new)
                .toList();

        return ResponseEntity.ok().body(responsePostList);
    }

    @GetMapping("/community/{id}")
    public ResponseEntity<ResponsePost> getCommunityDetail(@PathVariable Long id) {
        Post post = postService.getCommunityDetail(id);

        return ResponseEntity.ok().body(new ResponsePost(post));
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
    public ResponseEntity<Post> updateCommunityDetail(@PathVariable Long id, @RequestBody RequestPost requestPost) {
        Post updated = postService.updateCommunityDetail(id, requestPost);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/community/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deleteCommunity(id);
        return ResponseEntity.ok().build();
    }
}