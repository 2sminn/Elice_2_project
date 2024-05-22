package com.elice.kittyandpuppy.module.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "post_id", updatable = false)
    private Long postId;

    @Column(name = "member_id", updatable = false)
    private Long memberId;
}
