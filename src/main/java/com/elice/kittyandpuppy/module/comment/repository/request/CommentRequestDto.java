package com.elice.kittyandpuppy.module.comment.repository.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
    private Long parent_id;
}
