package com.movie.movieinfo.service.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("userRequest:" + userRequest);
        System.out.println("userRequest clientRegistration:" + userRequest.getClientRegistration());
        System.out.println("userRequestAccessToken::::::"+userRequest.getAccessToken());
        System.out.println("userRequestAttributes():::::::::::::"+super.loadUser(userRequest).getAttributes());
        System.out.println("oAuth2User.getAttributes():::::::::::::::::::::::::::::::::::" +oAuth2User.getAttributes());
        if("google".equals(registrationId)) {
            var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "sub");
        } else if ("kakao".equals(registrationId)) {
            var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "id");
        }
        throw new OAuth2AuthenticationException(new OAuth2Error("Unsupported_Login_Provider" ,"지원하지 않는 로그인 방식입니다", null));
    }

}
