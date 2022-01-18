package com.github.bom4.springboot.web.dto;

import com.github.bom4.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListReponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListReponseDto(Posts entity) {
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.author=entity.getAuthor();
        this.modifiedDate=entity.getModifiedDate();
    }
}
