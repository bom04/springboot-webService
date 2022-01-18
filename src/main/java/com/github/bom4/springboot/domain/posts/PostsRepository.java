package com.github.bom4.springboot.domain.posts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 해당 Entity 클래스와 함께 위치해야됨
public interface PostsRepository extends JpaRepository<Posts,Long> { // <Entity 클래스, pk 타입>
    @Query("select p from Posts p order by p.id desc") // @Query는 가독성을 위해 사용한거고 querydql이 훨씬 좋음
    List<Posts> findAllDesc();
}
