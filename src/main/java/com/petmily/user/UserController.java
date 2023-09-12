package com.petmily.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/mypage/{id}")
    public String userMyPage(@PathVariable int id, Model model, Principal principal) {
        String username = principal.getName();
        SiteUser siteUser = userService.getUser(username);
        model.addAttribute("siteUser", siteUser);
        return "user_mypage";
    }
}