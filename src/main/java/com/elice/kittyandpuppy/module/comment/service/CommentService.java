package com.elice.kittyandpuppy.module.comment.service;


import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.repository.CommentRepository;
import com.elice.kittyandpuppy.module.comment.repository.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(CommentRequest commentRequest) {
        return Comment.builder()
                .content(commentRequest.getContent())
                .parent_id(commentRequest.getParentId())
                .build();
    }

    @Transactional
    public Comment update(Long commentId, CommentRequest commentDto) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow();
        findComment.setContent(commentDto.getContent());
        return findComment;
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }

    public List<Comment> findByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

}
