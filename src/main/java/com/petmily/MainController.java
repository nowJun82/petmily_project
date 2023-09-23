package com.petmily;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String root() {
        return "main";
    }

    @GetMapping("login")
    public String login() {
        return "login_form";
    }

    @GetMapping("map")
    public String showMap() { return "map"; }
}