package com.petmily.answer;

import com.petmily.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByAuthor(SiteUser siteUser);
}