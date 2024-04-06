package com.elice.kittyandpuppy.module.post.repository;

import com.elice.kittyandpuppy.module.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
