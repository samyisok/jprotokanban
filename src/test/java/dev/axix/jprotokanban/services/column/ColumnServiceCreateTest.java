package dev.axix.jprotokanban.services.column;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.models.column.ColumnRepository;


@SpringBootTest
public class ColumnServiceCreateTest {

  @SpyBean
  ColumnService columnService;

  @MockBean
  ColumnRepository columnRepository;

  @Mock
  Column column;

  String title = "title";

  @Mock
  Board board;

  Long boardId = 42L;

  @Mock
  Logger log;

  @BeforeEach
  void setUp() {
    doReturn(log).when(columnService).getLog();
    doReturn(column).when(columnService).getNewColumn();
    doReturn(board).when(columnService).getBoard(boardId);
    when(columnRepository.save(column)).thenReturn(column);

  }

  @Test
  void testCreate() {
    Column columnResult = columnService.create(title, boardId);
    assertNotNull(columnResult);
    assertSame(column, columnResult);
    verify(columnService).getNewColumn();
    verify(column).setTitle(title);
    verify(columnService).getBoard(boardId);
    verify(board).addColumn(column);
    verify(log).info("Create Column: " + title + " at board: " + boardId);
    verify(columnRepository).save(column);
  }
}
