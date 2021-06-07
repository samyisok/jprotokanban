package com.example.jprotokanban.services.filter;

import java.util.Optional;
import com.example.jprotokanban.models.board.Board;
import com.example.jprotokanban.models.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultMailFilter implements IncomingMailFilterable {

  @Autowired
  private BoardRepository boardRepository;

  @Override
  public Long getColumnId() throws Exception {
    Optional<Board> boardOpt = boardRepository.findFirstBy();
    if (boardOpt.isPresent()) {
      Board board = boardOpt.get();
      return board.getColumns().get(0).getId();
    }

    throw new Exception("can't find default column");
  }

}
