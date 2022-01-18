package com.github.bom4.springboot.domain.posts;

import com.github.bom4.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // entity 클래스에서 getter은 자주 쓰되 setter은 절대 만들지 마!
@NoArgsConstructor // 기본 생성자를 파라미터 없이 생성
@Entity // jpa의 어노테이션(테이블과 링크될 클래스임을 나타냄): SalesManager.java->sales_manager table
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generatedValue로 pk의 생성 규칙 나타냄, auto_increment 적용
    private Long id;

    @Column(length=500, nullable=false) // 테이블의 칼럼을 나타냄
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title,String content,String author) {
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update(String title,String content) {
        this.title=title;
        this.content=content;
    }
}
