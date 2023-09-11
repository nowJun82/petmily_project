package com.petmily.comment;

import java.security.Principal;
import java.util.Optional;

import com.petmily.answer.Answer;
import com.petmily.answer.AnswerService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.petmily.user.SiteUser;
import com.petmily.user.UserService;

@AllArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    private AnswerService answerService;

    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/create/answer/{id}")
    public String createAnswerComment(CommentForm commentForm) {
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create/answer/{id}")
    public String createAnswerComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
                                      BindingResult bindingResult, Principal principal) {
        Optional<Answer> answer = Optional.ofNullable(this.answerService.getAnswer(id));
        Optional<SiteUser> user = Optional.ofNullable(this.userService.getUser(principal.getName()));
        if (answer.isPresent() && user.isPresent()) {
            if (bindingResult.hasErrors()) {
                return "comment_form";
            }
            Comment c = this.commentService.create(answer.get(), user.get(), commentForm.getContent());
            return String.format("redirect:/question/detail/%s", c.getAnswerId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            if (!c.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            commentForm.setContent(c.getContent());
        }
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult, Principal principal,
                                @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            if (!c.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            c = this.commentService.modify(c, commentForm.getContent());
            return String.format("redirect:/question/detail/%s", c.getAnswerId());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(Principal principal, @PathVariable("id") Integer id) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            if (!c.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            }
            this.commentService.delete(c);
            return String.format("redirect:/question/detail/%s", c.getAnswerId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
}