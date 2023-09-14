package com.petmily;

import com.petmily.question.Question;
import com.petmily.question.QuestionRepository;
import com.petmily.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class PetmilyApplicationTests {
	@Autowired
	private QuestionService questionService;
	@Test
	void testJpa() {
		for (int i = 1; i <= 130; i++) {
			String subject = String.format("팁 게시판 테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			String board ="팁 게시판";
			this.questionService.create(subject, content,board);
		}
	}
}