package com.github.bom4.springboot.config.auth;

import com.github.bom4.springboot.config.auth.dto.OAuthAttributes;
import com.github.bom4.springboot.config.auth.dto.SessionUser;
import com.github.bom4.springboot.domain.user.User;
import com.github.bom4.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate=new DefaultOAuth2UserService();
        OAuth2User oAuth2User=delegate.loadUser(userRequest);

        String registrationId=userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인을 진행중인 서비스를 구별하는 코드(네이버 로그인인지 구글 로그인인지)
        String userNameAttributeName=userRequest.getClientRegistration().getProviderDetails()
                                            .getUserInfoEndpoint().getUserNameAttributeName(); // 로그인 진행 키가 되는 필드값으로 pk와 비슷한 의미
        OAuthAttributes attributes=OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        User user=saveOrUpdate(attributes);
        httpSession.setAttribute("user",new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user=userRepository.findByEmail(attributes.getEmail())
                    .map(entity->entity.update(attributes.getName(),attributes.getPicture()))
                    .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}

