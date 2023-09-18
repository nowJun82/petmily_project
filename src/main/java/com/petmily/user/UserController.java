package com.petmily.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/QnA")
    public String QnA(){return "user_QnA";}
    @GetMapping("/inquiry")
    public String inquiry(){return "user_inquiry";}

    @GetMapping("/login")
    public String login(UserCreateForm userCreateForm){
        return "login_form";
    }

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "login_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "login_form";
        }
        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(),
                    userCreateForm.getEmail(), userCreateForm.getNickname());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "login_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "login_form";
        }
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myPage")
    public String userMyPage(Model model, Principal principal) {
        String username = principal.getName();
        SiteUser siteUser = userService.getUser(username);
        model.addAttribute("siteUser", siteUser);
        return "user_myPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String userModify(UserUpdateForm userUpdateForm, @PathVariable Long id) {
        SiteUser siteUser = this.userService.getUser(id);
        userUpdateForm.setPassword(siteUser.getPassword());
        userUpdateForm.setNickname(siteUser.getNickname());
        userUpdateForm.setEmail(siteUser.getEmail());
        return "user_modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String userModify(@Valid UserUpdateForm userUpdateForm, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "user_modify";
        }
        SiteUser siteUser = this.userService.getUser(id);

        String newPwd = (userUpdateForm.getPassword() == null) ? siteUser.getPassword() : userUpdateForm.getPassword();
        String newNn = (userUpdateForm.getNickname() == null) ? siteUser.getNickname() : userUpdateForm.getNickname();
        String newEm = (userUpdateForm.getEmail() == null) ? siteUser.getEmail() : userUpdateForm.getEmail();

        this.userService.modify(siteUser, newPwd, newNn, newEm);
        return "main";
    }
}