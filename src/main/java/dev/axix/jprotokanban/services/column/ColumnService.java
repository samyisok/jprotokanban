package dev.axix.jprotokanban.services.column;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.models.column.ColumnRepository;
import dev.axix.jprotokanban.services.board.BoardService;

@Service
public class ColumnService {
  private static final Logger log = LoggerFactory.getLogger(ColumnService.class);

  @Autowired
  private ColumnRepository columnRepository;

  @Autowired
  private BoardService boardService;

  Column getNewColumn() {
    return new Column();
  }

  Logger getLog() {
    return log;
  }

  Board getBoard(Long boardId) {
    return boardService.getBoard(boardId);
  }

  public Column create(String title, Long boardId) {
    Column column = getNewColumn();
    column.setTitle(title);
    Board board = getBoard(boardId);
    board.addColumn(column);
    getLog().info("Create Column: " + title + " at board: " + boardId);
    return columnRepository.save(column);
  }

  public Column getColumn(Long id) {
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
