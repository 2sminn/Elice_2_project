package com.elice.kittyandpuppy.module.comment.entity;


import com.elice.kittyandpuppy.global.BaseTimeEntity;
import com.elice.kittyandpuppy.module.comment.repository.response.CommentResponse;
import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    @Column(name="comment_content")
    private String content;

    @Column(name="comment_parent_id")
    private Long parent_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

//    public CommentResponse fromEntity(Comment comment){
//        re
//    }

}
