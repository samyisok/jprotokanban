package dev.axix.jprotokanban.services.board;

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
import dev.axix.jprotokanban.models.board.BoardRepository;

@SpringBootTest
public class BoardServiceCreateTest {

  @SpyBean
  BoardService boardService;

  @MockBean
  BoardRepository boardRepository;

  @Mock
  Board board;

  String title = "title";

  @Mock
  Logger log;

  @BeforeEach
  void setUp() {
    doReturn(board).when(boardService).getNewBoard();
    doReturn(log).when(boardService).getLog();
    when(boardRepository.save(board)).thenReturn(board);
  }

  @Test
  void testCreate() {
    Board boardResult = boardService.create(title);
    assertNotNull(boardResult);
    assertSame(board, boardResult);
    verify(board).setTitle(title);
    verify(log).info("Create Board: " + title);
    verify(boardRepository).save(board);
  }
}
