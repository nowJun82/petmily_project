package com.petmily.board;

import com.petmily.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
