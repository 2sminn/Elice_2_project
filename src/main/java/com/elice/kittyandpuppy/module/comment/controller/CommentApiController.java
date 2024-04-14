package com.elice.kittyandpuppy.module.comment.controller;


import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.repository.request.CommentRequest;
import com.elice.kittyandpuppy.module.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
