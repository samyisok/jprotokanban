package dev.axix.jprotokanban.services.board;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.board.BoardRepository;

@Service
public class BoardService {
  private static final Logger log = LoggerFactory.getLogger(BoardService.class);

  @Autowired
  public BoardRepository boardRepository;

  Logger getLog() {
    return log;
  }

  Board getNewBoard() {
    return new Board();
  }

  public Board create(String title) {
    Board board = getNewBoard();
    board.setTitle(title);
    getLog().info("Create Board: " + title);
    return boardRepository.save(board);
  }

  public Board getBoard(Long id) {
    Optional<Board> board = boardRepository.findById(id);
    if (board.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("board " + id + " not found!");
    }
    return board.get();
  }

  public Page<Board> getAllPageable(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }
}
