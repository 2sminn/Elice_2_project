package com.elice.kittyandpuppy.module.post.dto;

import com.elice.kittyandpuppy.module.post.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleDto {
    private Long id;
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
