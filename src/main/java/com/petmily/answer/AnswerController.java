package com.petmily.answer;

import com.petmily.question.Question;
import com.petmily.question.QuestionService;
import com.petmily.user.SiteUser;
import com.petmily.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/news/{id}")
    public String createNewsAnswer(Model model, @PathVariable("id") Integer id,
                                   @Valid AnswerForm answerForm, BindingResult bindingResult,Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "detail/news_detail";
        }
        this.answerService.create(question, answerForm.getContent(),siteUser);
        return String.format("redirect:/question/news/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/free/{id}")
    public String createFreeAnswer(Model model, @PathVariable("id") Integer id,
                                   @Valid AnswerForm answerForm, BindingResult bindingResult,Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "detail/free_detail";
        }
        this.answerService.create(question, answerForm.getContent(),siteUser);
        return String.format("redirect:/question/free/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/tip/{id}")
    public String createTipAnswer(Model model, @PathVariable("id") Integer id,
                                  @Valid AnswerForm answerForm, BindingResult bindingResult,Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "detail/tip_detail";
        }
        this.answerService.create(question, answerForm.getContent(),siteUser);
        return String.format("redirect:/question/tip/detail/%s", id);
    }
}