package com.elice.kittyandpuppy.module.post.dto;

import com.elice.kittyandpuppy.module.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestPost {
    private Long id;
    private String title;
    private String content;
    private int type;

    public Post toEntity() {
        return new Post(id, title, content, 1, LocalDateTime.now(), LocalDateTime.now());
    }
}
