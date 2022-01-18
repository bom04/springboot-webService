package com.github.bom4.springboot.service.posts;

import com.github.bom4.springboot.domain.posts.Posts;
import com.github.bom4.springboot.domain.posts.PostsRepository;
import com.github.bom4.springboot.web.dto.PostsListReponseDto;
import com.github.bom4.springboot.web.dto.PostsResponseDto;
import com.github.bom4.springboot.web.dto.PostsSaveRequestDto;
import com.github.bom4.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostRemove;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts=postsRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("해당 게시글이 없습니다.id="+id));
        posts.update(requestDto.getTitle(),requestDto.getContent()); // 따로 db에 update하는 쿼리가 없음-> 영속성 컨텍스트 덕분에 가능!
        // 영속성 컨텍스트는 엔티티를 영구 저장하는 환경을 뜻함. 쉽게 말해서 트랜잭션 안에서 데이터베이스의 데이터를 가져오면
        // 이 데이터를 엔티티는 영속성 컨텍스트가 유지된 상태이기 때문에 해당 값을 변경하기만 하면 트랜잭션이 끝날때 테이블에 변경된 부분을 반영해줌=더티 체킹
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity=postsRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되 조회 기능만 남겨놓기 때문에 조회 속도가 빨라짐(등록,수정,삭제가 없는 메소드에서 사용 추천)
    public List<PostsListReponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                    .map(PostsListReponseDto::new)
                    .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id) {
        Posts posts=postsRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("해당 게시글이 없습니다.id="+id));
        postsRepository.delete(posts);
    }
}
