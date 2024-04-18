package com.elice.kittyandpuppy.module.post.repository;

import com.elice.kittyandpuppy.module.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
