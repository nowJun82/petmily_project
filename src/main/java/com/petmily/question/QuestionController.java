package com.petmily.question;


import com.petmily.answer.AnswerForm;
import com.petmily.board.Board;
import com.petmily.board.BoardService;
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
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final BoardService boardService;

    @GetMapping(value = "/notification/detail/{id}")
    public String notificationDetail(Model model, @PathVariable("id") Integer id) {
        Question notificationQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", notificationQuestion);
        return "detail/notification_detail";
    }

    @GetMapping(value = "/news/detail/{id}")
    public String newsDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question newsQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", newsQuestion);
        return "detail/news_detail";
    }

    @GetMapping(value = "/free/detail/{id}")
    public String freeDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question freeQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", freeQuestion);
        return "detail/free_detail";
    }

    @GetMapping(value = "/tip/detail/{id}")
    public String tipDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question tipQuestion = this.questionService.getQuestion(id);
        model.addAttribute("question", tipQuestion);
        return "detail/tip_detail";
    }

    //리스트 함수
    @GetMapping("/notification")
    public String notification(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw, 1L);
        model.addAttribute("paging", paging);
        return "community/question_notification";
    }

    //리스트 함수
    @GetMapping("/news")
    public String news(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw, 2L);
        model.addAttribute("paging", paging);
        return "community/question_news";
    }

    //리스트 함수
    @GetMapping("/free")
    public String free(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw, 3L);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "community/question_free";
    }

    //리스트 함수
    @GetMapping("/tip")
    public String tip(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw, 4L);
        model.addAttribute("paging", paging);
        return "community/question_tip";
    }

    //글 생성 함수
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(Model model, QuestionForm questionForm) {
        model.addAttribute("boards", boardService.findAll());
        return "question_form";
    }

    //글 생성 함수
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(Model model, @Valid QuestionForm questionForm, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Board board = boardService.findById(questionForm.getBoardId()).get();

        this.questionService.create(board, questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/" + board.getCode();
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setBoardId(question.getBoard().getId());
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

        return String.format("redirect:/");
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

    @GetMapping("/user/getList")
    public String getQuestionsByUser(Model model, Principal principal) {
        String username = principal.getName();
        SiteUser user = userService.getUser(username);
        List<Question> userQuestions = questionService.findByAuthor(user);
        model.addAttribute("userQuestions", userQuestions);

        return "user_getList";
    }
}