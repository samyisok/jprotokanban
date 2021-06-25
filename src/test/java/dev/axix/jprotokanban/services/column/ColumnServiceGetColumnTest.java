package dev.axix.jprotokanban.services.column;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.models.column.ColumnRepository;

@SpringBootTest
public class ColumnServiceGetColumnTest {
  @SpyBean
  ColumnService columnService;

  @MockBean
  ColumnRepository columnRepository;

  @Mock
  Column column;

  Long columnId = 42L;

  @BeforeEach
  void setUp() {
    when(columnRepository.findById(columnId)).thenReturn(Optional.of(column));
  }

  @Test
  void testGetColumn() {
    Column columnResult = columnService.getColumn(columnId);
    assertNotNull(columnResult);
    assertSame(column, columnResult);
    verify(columnRepository).findById(columnId);
  }

  @Test
  void testGetColumnThrowException() {
    when(columnRepository.findById(columnId)).thenReturn(Optional.empty());
    Exception e = assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
        () -> columnService.getColumn(columnId));
    assertEquals("Entity not found (column 42 not found!)", e.getMessage());
  }
}
