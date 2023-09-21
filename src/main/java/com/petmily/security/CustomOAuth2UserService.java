package com.petmily.security;

import com.petmily.user.SiteUser;
import com.petmily.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service  // 이 클래스를 스프링의 서비스 빈으로 등록
@Transactional(readOnly = true) // 모든 트랜잭션은 기본적으로 읽기 전용
@RequiredArgsConstructor // Lombok을 사용하여 final 필드나 @NonNull 필드의 생성자를 자동으로 생성
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    // 카카오톡 로그인이 성공할 때 마다 이 함수가 실행된다.
    @Override
    @Transactional // 이 메소드에 대한 트랙잭션 설정 (기본은 읽기/쓰기 모두 가능)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth2 로그인 후 기본 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자의 소셜 계정 고유 ID를 저장할 변수
        String oauthId;

        // 사용자의 소셜 계정 닉네임을 저장할 변수
        String nickname;

        // 사용자가 선택한 소셜 로그인 제공자의 종류를 저장할 변수 (예: NAVER, GOOGLE 등)
        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        // 네이버 로그인을 선택한 사용자 정보 추출
        if ("NAVER".equals(providerTypeCode)) {
            Map<String, Object> naverAttributes = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            oauthId = (String) naverAttributes.get("id");
            nickname = (String) naverAttributes.get("nickname");

        // 구글 로그인을 선택한 사용자 정보 추출
        } else if ("GOOGLE".equals(providerTypeCode)) {
            oauthId = oAuth2User.getName();
            nickname = (String) oAuth2User.getAttributes().get("name");

        // 카카오 로그인을 선택한 사용자 정보 추출
        } else if ("KAKAO".equals(providerTypeCode)) {
            Map<String, Object> kakaoAttributes = (Map<String, Object>) oAuth2User.getAttributes();
            oauthId = String.valueOf(kakaoAttributes.get("id"));
            nickname = (String) ((Map) kakaoAttributes.get("properties")).get("nickname");


        // 다른 소셜 로그인 서비스를 선택한 경우의 기본 처리
        } else {
            oauthId = oAuth2User.getName();
            nickname = (String) oAuth2User.getAttributes().get("nickname"); // 기본 처리
        }
        // DB에 저장될 유저네임 형식 생성
        String username = providerTypeCode + "__%s".formatted(oauthId);

        // 소셜 로그인 후 사용자 처리 로직 (DB에 저장이 되거나 업데이트)
        SiteUser siteUser = userService.whenSocialLogin(providerTypeCode, username, nickname);

        return siteUser;
    }
}