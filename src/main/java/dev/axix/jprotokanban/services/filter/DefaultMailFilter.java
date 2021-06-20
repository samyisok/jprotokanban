package dev.axix.jprotokanban.services.filter;

import java.util.Optional;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultMailFilter implements IncomingMailFilterable {

  @Autowired
  private BoardRepository boardRepository;

  @Override
  public Long getColumnId() throws Exception {
    Optional<Board> boardOpt = boardRepository.findFirstByColumnsIsNotNull();
    if (boardOpt.isPresent()) {
      Board board = boardOpt.get();
      return board.getColumns().get(0).getId();
    }

    throw new Exception("can't find default column");
  }

}
