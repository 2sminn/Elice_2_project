package com.elice.kittyandpuppy.module.post.dto;

import com.elice.kittyandpuppy.module.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPost {

    private String title;
    private String content;
    private int type;
    private int views;

    public Post toEntity(RequestPost requestPost) {
        return Post.builder()
                .title(requestPost.getTitle())
                .content(requestPost.getContent())
                .type(requestPost.getType())
                .views(requestPost.getViews())
                .build();
    }
}