package com.petmily.comment;

import java.time.LocalDateTime;
import java.util.Optional;

import com.petmily.answer.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.petmily.user.SiteUser;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;

    public Comment create(Answer answer, SiteUser author, String content) {
        Comment c = new Comment();
        c.setContent(content);
        c.setCreateDate(LocalDateTime.now());
        c.setAnswer(answer);
        c.setAuthor(author);
        c = this.commentRepository.save(c);
        return c;
    }

    public Optional<Comment> getComment(Integer id) {
        return this.commentRepository.findById(id);
    }

    public Comment modify(Comment c, String content) {
        c.setContent(content);
        c.setModifyDate(LocalDateTime.now());
        c = this.commentRepository.save(c);
        return c;
    }

    public void delete(Comment c) {
        this.commentRepository.delete(c);
    }
}