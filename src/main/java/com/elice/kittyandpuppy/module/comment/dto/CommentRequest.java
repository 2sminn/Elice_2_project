package com.elice.kittyandpuppy.module.comment.dto;

import com.elice.kittyandpuppy.module.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String content;
    private Long postId;
}
