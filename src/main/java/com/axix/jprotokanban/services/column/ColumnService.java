package com.axix.jprotokanban.services.column;

import java.util.Optional;
import com.axix.jprotokanban.controllers.column.ColumnCreateInput;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.board.Board;
import com.axix.jprotokanban.models.board.BoardRepository;
import com.axix.jprotokanban.models.column.Column;
import com.axix.jprotokanban.models.column.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ColumnService {
  @Autowired
  private ColumnRepository columnRepository;

  @Autowired
  private BoardRepository boardRepository;

  public Column create(ColumnCreateInput input) {
    Column column = new Column();
    column.setTitle(input.getTitle());
    Optional<Board> boardOpt = boardRepository.findById(input.getBoardId());
    if (boardOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("board " + input.getBoardId() + " not found!");
    }
    Board board = boardOpt.get();
    board.addColumn(column);
    return columnRepository.save(column);
  }

  public Column getBoard(Long id) {
    Optional<Column> column = columnRepository.findById(id);
    if (column.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("column " + id + " not found!");
    }
    return column.get();
  }

  public Page<Column> getAllPageable(Pageable pageable) {
    return columnRepository.findAll(pageable);
  }

}
