package com.petmily.user;

import com.petmily.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.datatransfer.Clipboard;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String password, String email, String nickname) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
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

//    public SiteUser whenSocialLogin(String providerTypeCode, String username) {
//        Optional<SiteUser> opSiteUser = findByUsername(username);
//
//        if (opSiteUser.isPresent()) return RsData.of("S-1", "로그인 되었습니다.", opSiteUser.get());
//
//        return join(providerTypeCode, username, "");
//    }
}