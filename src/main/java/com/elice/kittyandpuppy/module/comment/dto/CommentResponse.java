package com.elice.kittyandpuppy.module.comment.dto;

import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private Long parentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//    private Member member;
//    private Post post;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParent_id();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
