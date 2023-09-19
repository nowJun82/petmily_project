package com.petmily.question;


import com.petmily.answer.AnswerForm;
import com.petmily.user.SiteUser;
import com.petmily.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/notification")
    public String notification(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(1,page);
        model.addAttribute("paging", paging);
        return "community/question_notification";
    }

    @GetMapping(value = "/notification/detail/{id}")
    public String notificationDetail(Model model, @PathVariable("id") Integer id) {
        Question notificationQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", notificationQuestion);
        return "detail/notification_detail";
    }

    @GetMapping("/news")
    public String news(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(2,page);
        model.addAttribute("paging", paging);
        return "community/question_news";
    }
    @GetMapping(value = "/news/detail/{id}")
    public String newsDetail(Model model, @PathVariable("id") Integer id,AnswerForm answerForm) {
        Question newsQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", newsQuestion);
        return "detail/news_detail";
    }

    @GetMapping("/free")
    public String free(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(3,page);
        model.addAttribute("paging", paging);
        return "community/question_free";
    }
    @GetMapping(value = "/free/detail/{id}")
    public String freeDetail(Model model, @PathVariable("id") Integer id,AnswerForm answerForm) {
        Question freeQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", freeQuestion);
        return "detail/free_detail";
    }
    @GetMapping("/tip")
    public String tip(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(4,page);
        model.addAttribute("paging", paging);
        return "community/question_tip";
    }
    @GetMapping(value = "/tip/detail/{id}")
    public String tipDetail(Model model, @PathVariable("id") Integer id,AnswerForm answerForm) {
        Question tipQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", tipQuestion);
        return "detail/tip_detail";
    }

//    @GetMapping("/list")
//    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
//                       @RequestParam(value = "kw", defaultValue = "") String kw) {
//        Page<Question> paging = this.questionService.getList(page, kw);
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);
//        return "question_list";
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(),questionForm.getBoard());
//        if(){
        if(questionForm.getBoard().equals("뉴스 게시판")){
            return "redirect:/question/news";
        }
        else if(questionForm.getBoard().equals("자유 게시판")){
            return "redirect:/question/free";
        }
        else if(questionForm.getBoard().equals("팁 게시판")){
            return "redirect:/question/tip";
        }
        return "redirect:/question/news"; // 질문 저장후 질문목록으로 이동
    }

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/create")
//    public String questionCreate(@Valid QuestionForm questionForm,
//                                 BindingResult bindingResult, Principal principal) {
//        if (bindingResult.hasErrors()) {
//            return "question_form";
//        }
//        SiteUser siteUser = this.userService.getUser(principal.getName());
//        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
//        return "redirect:/question/list";
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}