package com.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.column.Column;
import com.axix.jprotokanban.models.column.ColumnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class CardServiceGetColumnFromParamsTest {

  @SpyBean
  CardService cardService;

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
  void testGetColumnFromParams() {
    Column columnResult = cardService.getColumnFromParams(columnId);
    assertNotNull(columnResult);
  }

  @Test
  void testGetColumnFromParamsThrowException() {
    when(columnRepository.findById(columnId)).thenReturn(Optional.empty());
    Exception e = assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
        () -> cardService.getColumnFromParams(columnId));

    assertEquals("Entity not found (column 42 not found!)", e.getMessage());
  }

}
