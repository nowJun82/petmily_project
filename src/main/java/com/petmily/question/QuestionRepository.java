package com.petmily.question;

import com.petmily.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);

    List<Question> findByBoard(Board board);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findByBoard(Board board, Pageable pageable);

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    @Query("""
            select distinct q
            from Question q
            left outer join SiteUser m1 on q.author = m1 
            left outer join Answer a on a.question = q 
            left outer join SiteUser m2 on a.author = m2 
            where q.board.id = :boardId
            and (
              q.subject like %:kw% 
              or q.content like %:kw% 
              or m1.username like %:kw% 
              or a.content like %:kw% 
              or m2.username like %:kw%
            )
            """)
    Page<Question> findAllByKeyword(@Param("boardId") long boardId, @Param("kw") String kw, Pageable pageable);

}
