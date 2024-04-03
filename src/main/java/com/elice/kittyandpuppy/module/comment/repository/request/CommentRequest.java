package com.elice.kittyandpuppy.module.comment.repository.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private Long id;
    private String content;
    private Long parent_id;
}
