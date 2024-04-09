package com.elice.kittyandpuppy.module.post.entity;

import com.elice.kittyandpuppy.module.comment.entity.Comment;
import com.elice.kittyandpuppy.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "post")
public class Post {
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

//    @Column(name = "views")
//    private int views;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Column(name = "type")
    private int type;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    public Post(Long id, String title, String content, int type, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void updatePatch(Post post) {
        if (post.title != null)
            this.title = post.title;

        if (post.content != null)
            this.content = post.content;
    }
}
