package com.elice.kittyandpuppy.module.comment.entity;


import com.elice.kittyandpuppy.global.BaseTimeEntity;
import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "comment")
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="parent_id")
    private Long parent_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


}
