package com.petmily;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {


    private final CommonUtil ut;
    private boolean isTestDataCreated = false;

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    @GetMapping("/")
    public String root() {

        if(!isTestDataCreated){
            ut.dataCreaete();
            isTestDataCreated = true;
        }
        return "main";
    }

    @GetMapping("login")
    public String login() {
        return "login_form";
    }

    @GetMapping("map")
    public String showMap() { return "map"; }
}