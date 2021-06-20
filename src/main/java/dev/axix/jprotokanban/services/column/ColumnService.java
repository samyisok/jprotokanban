package dev.axix.jprotokanban.services.column;

import java.util.Optional;
import dev.axix.jprotokanban.controllers.column.ColumnCreateInput;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.board.BoardRepository;
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.models.column.ColumnRepository;
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
