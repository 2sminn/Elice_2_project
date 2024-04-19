package com.elice.kittyandpuppy.module.post.entity;

import com.elice.kittyandpuppy.global.BaseTimeEntity;
import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "post")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "views")
    private int views;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Column(name = "type")
    private int type;


    public Post(Long id, String title, String content, int type){
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public void updatePatch(Post post) {
        if (post.title != null)
            this.title = post.title;

        if (post.content != null)
            this.content = post.content;
    }

    public void incrementViewCount() {
        this.views++;
    }
}