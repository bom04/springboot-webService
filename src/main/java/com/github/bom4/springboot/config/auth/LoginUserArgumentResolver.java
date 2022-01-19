package com.github.bom4.springboot.config.auth;

import com.github.bom4.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    // HandlerMethodArgumentResolver: 조건에 맞는 경우 메소드가 있으면 해당 클래스가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있음
    
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { // 컨트롤러의 메서드에서 특정 파라미터를 지원하는지 판단
        boolean isLoginUserAnnotation=parameter.getParameterAnnotation(LoginUser.class)!=null; // 파라미터에 @LoginUser 어노테이션이 붙어있으면 true
        boolean isUserClass= SessionUser.class.equals(parameter.getParameterType()); // 파라미터의 클래스 타입이 SessionUser인 경우 true
        return isLoginUserAnnotation && isUserClass;
    }

    // 위의 supportsParameter가 true를 리턴하면 컨트롤러의 메소드의 파라미터에 전달할 객체 리턴
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
