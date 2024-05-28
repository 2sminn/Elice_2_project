package com.elice.kittyandpuppy.module.comment.controller;


import com.elice.kittyandpuppy.module.comment.dto.CommentResponse;
import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.dto.CommentRequest;
import com.elice.kittyandpuppy.module.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {
    private final CommentService commentService;




    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody final CommentRequest commentRequest) {
        Comment comment = commentService.save(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("commentId") Long commentId,
                                                 @RequestBody final CommentRequest commentRequest) {
        Comment comment = commentService.update(commentId, commentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(commentId);
    }

    @Operation(summary = "댓글 조회", description = "게시물 ID에 따라서 댓글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> findByPost(@PathVariable("postId") Long postId) {
        List<CommentResponse> comments = commentService.findByPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }


}
