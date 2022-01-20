package com.github.bom4.springboot.web;


import com.github.bom4.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class) // JUnit에 내장된 실행자 말고 springRunner라는 스프링 실행자를 실행함(스프링 부트 테스트와 JUnit사이에 연결자 역할)-Junit4 사용
// @WebMvcTest는 MVC 테스트에 에 적합한 애노테이션, @Controller만 테스트하는 어노테이션
// -> 그래서 SecurityConfig는 읽었어도 securityConfig를 생성하기 위해 필요한, @Service인 CustomOAuth2UserService는 읽지 못한 거임
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
            @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,classes= SecurityConfig.class)
        }
) // 그래서 스캔 대상에서 아얘 SecurityConfig를 제거하겠다, 근데 실사용은 비추임.
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 bean을 주입받음
    private MockMvc mvc; // 웹 api를 테스트할때 사용, 이걸 통해 http의 get,post 등의 api를 테스트 할 수 있음

    @WithMockUser(roles="USER")
    @Test
    public void hello리턴() throws Exception {
        String hello="hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk()) // perform의 결과를 검증(HTTP Header의 status<200,400,500 등>의 상태 검증), isOk==200
                .andExpect(content().string(hello)); // 응답 본문의 내용 검증
    }
    @WithMockUser(roles="USER")
    @Test
    public void helloDto리턴() throws Exception {
        String name="hello";
        int amount=1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto")
                .param("name",name) // api 요청에 사용될 파라미터 설정(string만 가능)
                .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name))) // jsonPath는 json 응답값을 필드별로 검증할 수 있음
                .andExpect(jsonPath("$.aa",is("123")))
                .andExpect((ResultMatcher) jsonPath("$.amount",is(amount)));
    }
}
