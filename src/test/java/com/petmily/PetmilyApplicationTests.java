package com.petmily;

import com.petmily.board.Article;
import com.petmily.board.ArticleRepository;
import com.petmily.board.ArticleService;
import com.petmily.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class PetmilyApplicationTests {

    @Autowired
	private ArticleRepository articleRepository;

	@Test
	void testJpa() {
		Article article = new Article();
		article.setSubject("제목1");
		article.setContent("내용1");
		article.setCreateDate(LocalDateTime.now());
		this.articleRepository.save(article);  // 첫번째 질문 저장

		Article article1 = new Article();
		article1.setSubject("제목2");
		article1.setContent("내용2");
		article1.setCreateDate(LocalDateTime.now());
		this.articleRepository.save(article1);  // 첫번째 질문 저장

		Article article2 = new Article();
		article2.setSubject("제목3");
		article2.setContent("내용3");
		article2.setCreateDate(LocalDateTime.now());
		this.articleRepository.save(article2);  // 첫번째 질문 저장
	}
}