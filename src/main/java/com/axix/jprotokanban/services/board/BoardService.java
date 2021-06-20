package com.axix.jprotokanban.services.board;

import java.util.Optional;
import com.axix.jprotokanban.controllers.board.BoardCreateInput;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.board.Board;
import com.axix.jprotokanban.models.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<Board> getAllPageable(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }
}
