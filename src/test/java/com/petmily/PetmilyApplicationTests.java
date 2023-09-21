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
	}
}