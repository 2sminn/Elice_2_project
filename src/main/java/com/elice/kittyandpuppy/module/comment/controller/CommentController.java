package com.elice.kittyandpuppy.module.comment.controller;


import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.repository.request.CommentRequest;
import com.elice.kittyandpuppy.module.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comment")
    public Comment createComment(@RequestBody final CommentRequest commentRequest){
        return commentService.save(commentRequest);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comment")
    public Comment updateComment(@RequestBody final CommentRequest commentRequest){
        return commentService.update(commentRequest.getId(), commentRequest);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "댓글 "+commentId+"번 삭제 완료";
    }

}
