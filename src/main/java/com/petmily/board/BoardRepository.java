package com.petmily.board;

import com.petmily.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
