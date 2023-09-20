package com.petmily.security;

import com.petmily.user.SiteUser;
import com.petmily.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    // 카카오톡 로그인이 성공할 때 마다 이 함수가 실행된다.
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String oauthId = oAuth2User.getName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map attributesProperties = (Map) attributes.get("properties");
        String nickname = (String) attributesProperties.get("nickname");

        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        oauthId = switch (providerTypeCode) { // 추가 한 내용들 오류 발생 방지
            case "NAVER" -> ((Map<String, String>) oAuth2User.getAttributes().get("response")).get("id");
            default -> oAuth2User.getName();
        }; // 여기까지

        String username = providerTypeCode + "__%s".formatted(oauthId);

        SiteUser siteUser = userService.whenSocialLogin(providerTypeCode, username, nickname);

        return new CustomOAuth2User(siteUser.getUsername(), siteUser.getPassword(), siteUser.getGrantedAuthorities());
    }
}

class CustomOAuth2User extends User implements OAuth2User {

    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return getUsername();

    }
}