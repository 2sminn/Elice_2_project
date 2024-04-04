package com.elice.kittyandpuppy.module.comment.service;


import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.repository.CommentRepository;
import com.elice.kittyandpuppy.module.comment.repository.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(CommentRequest commentDto){
        return Comment.builder()
                .content(commentDto.getContent())
                .parent_id(commentDto.getParent_id())
                .build();
    }

    public Comment update(Long commentId, CommentRequest commentDto){
        Comment findComment = commentRepository.findById(commentId).orElseThrow();
        findComment.setContent(commentDto.getContent());
        return findComment;
    }
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }
    public List<Comment> findedByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }
    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }
}
