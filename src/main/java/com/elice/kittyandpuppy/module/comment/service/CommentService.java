package com.elice.kittyandpuppy.module.comment.service;


import com.elice.kittyandpuppy.module.comment.dto.CommentResponse;
import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.comment.repository.CommentRepository;
import com.elice.kittyandpuppy.module.comment.dto.CommentRequest;
import com.elice.kittyandpuppy.module.post.dto.ResponsePost;
import com.elice.kittyandpuppy.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment save(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .post(postRepository.findById(commentRequest.getPostId()).orElseThrow())
                .build();
        return commentRepository.save(comment);
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

    public List<CommentResponse> findByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentResponse::new)
                .toList();
        return commentResponses;
    }


}
