package dev.axix.jprotokanban.services.board;

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
import dev.axix.jprotokanban.models.board.BoardRepository;

@SpringBootTest
public class BoardServiceTest {

  @SpyBean
  BoardService boardService;

  @MockBean
  BoardRepository boardRepository;

  @Mock
  Page<Board> page;

  @Mock
  Pageable pageable;

  @Test
  void testGetAllPageable() {
    when(boardRepository.findAll(pageable)).thenReturn(page);
    Page<Board> pageResult = boardService.getAllPageable(pageable);
    assertNotNull(pageResult);
    assertSame(page, pageResult);
    verify(boardRepository).findAll(pageable);
  }

  @Test
  void testGetLog() {
    Logger log = boardService.getLog();
    assertNotNull(log);
  }

  @Test
  void testGetNewBoard() {
    Board board = boardService.getNewBoard();
    assertNotNull(board);
  }
}
