package com.petmily.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateForm {
    private String username;
    private String password;
    private String nickname;
    private String email;
}