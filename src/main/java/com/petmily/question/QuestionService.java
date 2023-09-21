package com.petmily.question;

import com.petmily.DataNotFoundException;
import com.petmily.answer.Answer;
import com.petmily.user.SiteUser;
import com.petmily.user.UserRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void create(String subject, String content, String board,SiteUser user) {
        Integer boardid=0;

        if(board.equals("뉴스 게시판")){
            boardid=2;
        }
        else if(board.equals("자유 게시판")){
            boardid=3;
        }
        else if(board.equals("팁 게시판")){
            boardid=4;
        }
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setBoard(boardid);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user); // -> 여기서 member 를 추가 시켜야 소셜 로그인에서 게시물을 쓸 수 있다.
        this.questionRepository.save(q);
    }

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    public Page<Question> getList(Integer board, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        return this.questionRepository.findByBoard(board, pageable);
//        return this.questionRepository.findQuestionsByKeywordAndBoard(board,pageable,spec);
    }


    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public List<Question> getListByBoard(Integer board) {
        return this.questionRepository.findByBoard(board);
    }

    public List<Question> findByAuthor(SiteUser author) {
        return questionRepository.findByAuthor(author);
    }
}


