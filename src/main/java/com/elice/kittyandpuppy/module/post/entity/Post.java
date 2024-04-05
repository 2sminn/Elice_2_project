package com.elice.kittyandpuppy.module.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_content", columnDefinition = "TEXT")
    private String content;

//    @Column(name = "post_views")
//    private int views;

    @Column(name = "post_type")
    private int type;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    public void updatePatch(Post post) {
        if (post.title != null)
            this.title = post.title;

        if (post.content != null)
            this.content = post.content;
    }
}
