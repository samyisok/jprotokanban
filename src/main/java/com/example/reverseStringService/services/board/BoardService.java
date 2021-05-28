package com.example.reverseStringService.services.board;

import java.util.Optional;
import com.example.reverseStringService.controllers.board.BoardCreateInput;
import com.example.reverseStringService.exceptions.custom.CodeExceptionManager;
import com.example.reverseStringService.models.board.Board;
import com.example.reverseStringService.models.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
  @Autowired
  public BoardRepository boardRepository;

  public Board create(BoardCreateInput input) {
    Board board = new Board();
    board.setTitle(input.getTitle());
    return boardRepository.save(board);
  }

  public Board getBoard(Long id) {
    Optional<Board> board = boardRepository.findById(id);
    if (board.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND.getThrowableException();
    }
    return board.get();
  }
}
