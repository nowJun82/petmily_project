package com.petmily.user;

import com.petmily.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String password, String email, String nickname) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        /*user.setEmail(email);*/
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    @Transactional
    public SiteUser whenSocialLogin(String providerTypeCode, String username, String nickname) {
        Optional<SiteUser> oSiteUser = findByUsername(username);

        if (oSiteUser.isPresent()) return oSiteUser.get();

        // 소셜 로그인를 통한 가입시 비번은 없다.
        return create(username, "", "",nickname); // 최초 로그인 시 딱 한번 실행
    }

    private Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    public SiteUser whenSocialLogin(String providerTypeCode, String username) {
//        Optional<SiteUser> opSiteUser = findByUsername(username);
//
//        if (opSiteUser.isPresent()) return RsData.of("S-1", "로그인 되었습니다.", opSiteUser.get());
//
//        return join(providerTypeCode, username, "");
//    }
}