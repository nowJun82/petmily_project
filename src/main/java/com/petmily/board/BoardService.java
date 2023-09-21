package com.petmily.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board create(String name) {
        Board board = Board
                .builder()
                .name(name)
                .build();

        boardRepository.save(board);

        return board;
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(long id) {
        return boardRepository.findById(id);
    }
}
