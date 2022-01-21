package com.github.bom4.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // jpa entity 클래스가 해당 클래스를 상속할 경우 이 필드들도 칼럼으로 인식하도록함(엔티티들의 공통된 필드를 따로 뺀 느낌)
@EntityListeners(AuditingEntityListener.class) // 이 클래스에 Auditing 기능을 포함시키겠다(엔티티를 감시해서 변화가 있을때마다 자동으로 값을 넣어주겠다)
public abstract class BaseTimeEntity {
    @CreatedDate // 엔티티가 생성되어 저장될때 시간이 자동 저장됨
    private LocalDateTime createdDate;

    @LastModifiedDate // 엔티티의 값을 변경할 때 시간이 자동 저장됨
    private LocalDateTime modifiedDate;
}
