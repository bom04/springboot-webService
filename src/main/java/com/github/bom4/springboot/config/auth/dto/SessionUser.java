package com.github.bom4.springboot.config.auth.dto;

import com.github.bom4.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // 인증된(로그인된) 사용자 정보 저장 ->근데 왜 User과 똑같은데 따로 dto를 만들었나?
    // User 클래스를 세션에 저장하면 User 클래스에 직렬화가 구현되지 않아서 에러 발생 -> 직렬화된 dto를 만들자
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name=user.getName();
        this.email=user.getEmail();
        this.picture=user.getPicture();
    }
}
