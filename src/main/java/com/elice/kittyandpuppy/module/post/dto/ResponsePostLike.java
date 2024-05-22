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
public class ResponsePostLike {
    private Long id;
    private String title;
    private String content;
    private int type;
    private int views;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ResponsePostLike(Post post, int likeCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.type = post.getType();
        this.views = post.getViews();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.likeCount = likeCount;
    }
}
