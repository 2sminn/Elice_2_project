package com.elice.kittyandpuppy.module.post.controller;


import com.elice.kittyandpuppy.module.post.dto.RequestPost;
import com.elice.kittyandpuppy.module.post.dto.ResponsePost;
import com.elice.kittyandpuppy.module.post.entity.Post;
import com.elice.kittyandpuppy.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostRestController {
    private final PostService postService;

    // Community 관련 컨트롤러
    // GET
    @GetMapping("/communities")
    public ResponseEntity<Page<ResponsePost>> getCommunityList(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
                                                               Pageable pageable) {
        Page<ResponsePost> responsePosts = postService.getCommunityList(pageable);
        return ResponseEntity.ok().body(responsePosts);
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
