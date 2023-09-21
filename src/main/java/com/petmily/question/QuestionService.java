package com.petmily.question;

import com.petmily.DataNotFoundException;
import com.petmily.board.Board;
import com.petmily.user.SiteUser;
import com.petmily.user.UserRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);

        if (question.isPresent()) {
            Question question1 = question.get();
            question1.setView(question1.getView() + 1);
            this.questionRepository.save(question1);
            return question1;
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Page<Question> getList(int page, String kw, long boardId) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAllByKeyword(boardId, kw, pageable);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void create(Board board, String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setBoard(board);
        q.setAuthor(user);
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    public List<Question> findByAuthor(SiteUser author) {
        return questionRepository.findByAuthor(author);
    }
}