package dev.axix.jprotokanban.services.column;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.models.column.ColumnRepository;
import dev.axix.jprotokanban.services.board.BoardService;

@SpringBootTest
public class ColumnServiceTest {
  @SpyBean
  ColumnService columnService;

  @MockBean
  BoardService boardService;

  @MockBean
  ColumnRepository columnRepository;

  @Mock
  Board board;

  Long boardId = 42L;

  @Mock
  Page<Column> page;

  @Mock
  Pageable pageable;


  @Test
  void testGetBoard() {
    when(boardService.getBoard(boardId)).thenReturn(board);
    Board boardResult = columnService.getBoard(boardId);
    assertNotNull(boardResult);
    assertSame(board, boardResult);
    verify(boardService).getBoard(boardId);
  }

  @Test
  void testGetLog() {
    Logger log = columnService.getLog();
    assertNotNull(log);
  }

  @Test
  void testGetNewColumn() {
    Column column = columnService.getNewColumn();
    assertNotNull(column);
  }

  @Test
  void testGetAllPageable() {
    when(columnRepository.findAll(pageable)).thenReturn(page);
    Page<Column> pageResult = columnService.getAllPageable(pageable);
    assertNotNull(pageResult);
    assertSame(page, pageResult);
    verify(columnRepository).findAll(pageable);
  }
}
