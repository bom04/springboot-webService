package com.github.bom4.springboot.config.auth;

import com.github.bom4.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // spring security의 설정들을 활성화 시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable함
                .and()
                    .authorizeRequests() // url별 권한 관리를 설정하는 옵션의 시작점
                    // antMatchers는 권한 관리 대상을 지정하는 옵션이므로 url,http 메소드를 적을 수 있음
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/profile").permitAll() // 해당 url은 모두 전체 열람 허용하겠다
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 해당 url은 USER권한을 가진 사람만 이용할 수 있게 하겠다
                    .anyRequest().authenticated() // 위에서 설정된 값들 이외의 url 요청이 오면 인증된 사람들(로그인한 사용자)한테만 허용하겠다
                .and()
                    .logout() // 로그아웃 기능에 대한 설정의 진입점
                        .logoutSuccessUrl("/") // 로그아웃 성공시 /주소로 이동하겠다
                .and()
                    .oauth2Login() // oauth2 로그인 기능에 대한 설정의 진입점
                        .userInfoEndpoint() // end point: 소프트웨어의 최종 목적지(끝)인 사용자를 뜻함 ->로그인 성공 후 사용자 정보를 가져올 설정을 담당
                            .userService(customOAuth2UserService); // 로그인 성공 후 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록해 소셜 서비스에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음
    }
}
