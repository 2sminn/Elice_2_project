package com.elice.kittyandpuppy.module.comment.service;


import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.repository.CommentRepository;
import com.elice.kittyandpuppy.module.comment.repository.request.CommentRequestDto;
import com.elice.kittyandpuppy.module.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private Comment createComment(CommentRequestDto commentDto){
        return Comment.builder()
                .content(commentDto.getContent())
                .parent_id(commentDto.getParent_id())
                .build();
    }

    private Comment updateComment(Long commentId, CommentRequestDto commentDto){
        Comment findComment = commentRepository.findById(commentId).orElseThrow();
        findComment.setContent(commentDto.getContent());
        return findComment;
    }
    private Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }
    private List<Comment> findedByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }
    private void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }
}
