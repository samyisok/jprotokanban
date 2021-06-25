package dev.axix.jprotokanban.services.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.models.board.Board;
import dev.axix.jprotokanban.models.board.BoardRepository;


@SpringBootTest
public class BoardServiceGetBoardTest {

  @SpyBean
  BoardService boardService;

  @MockBean
  BoardRepository boardRepository;

  @Mock
  Board board;

  Long boardId = 42L;

  @BeforeEach
  void setUp() {
    when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
  }

  @Test
  void testGetBoard() {
    Board boardResult = boardService.getBoard(boardId);
    assertNotNull(boardResult);
    assertSame(board, boardResult);
    verify(boardRepository).findById(boardId);
  }

  @Test
  void testGetBoardThrowException() {
    when(boardRepository.findById(boardId)).thenReturn(Optional.empty());
    Exception e = assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
        () -> boardService.getBoard(boardId));

    assertEquals("Entity not found (board 42 not found!)", e.getMessage());
  }
}
