package com.example.jprotokanban.services.column;

import java.util.Optional;
import com.example.jprotokanban.controllers.column.ColumnCreateInput;
import com.example.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.example.jprotokanban.models.board.Board;
import com.example.jprotokanban.models.board.BoardRepository;
import com.example.jprotokanban.models.column.Column;
import com.example.jprotokanban.models.column.ColumnRepository;
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
