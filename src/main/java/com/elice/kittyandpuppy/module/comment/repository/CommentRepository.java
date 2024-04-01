package com.elice.kittyandpuppy.module.comment.repository;

import com.elice.kittyandpuppy.module.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPostId(Long postId);
}
