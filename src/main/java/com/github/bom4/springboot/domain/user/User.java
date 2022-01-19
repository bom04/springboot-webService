package com.github.bom4.springboot.domain.user;

import com.github.bom4.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;
    
    @Enumerated(EnumType.STRING) // enum type을 기본 int가 아니라 string으로 저장해서 무슨 코드를 의미하는지 바로 찾겠다
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name,String email,String picture,Role role) {
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;
    }

    public User update(String name,String picture) {
        this.name=name;
        this.picture=picture;
        return this;
    }
    public String getRoleKey() {
        return this.role.getKey();
    }


}
