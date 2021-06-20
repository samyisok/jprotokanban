package com.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.axix.jprotokanban.models.card.Card;
import com.axix.jprotokanban.models.card.CardRepository;
import com.axix.jprotokanban.models.column.Column;
import com.axix.jprotokanban.models.customer.Customer;
import com.axix.jprotokanban.services.card.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class CardServiceCreateFromCustomerTest {

  @SpyBean
  CardService cardService;

  String title = "title";
  String text = "text";
  Long columnId = 42L;

  @Mock
  Customer customer;

  @Mock
  Column column;

  @Mock
  Card card;

  @MockBean
  CardRepository cardRepository;

  @BeforeEach
  void setUp() {
    doReturn(card).when(cardService).getNewCard();
    doReturn(column).when(cardService).getColumnFromParams(columnId);
    when(cardRepository.save(card)).thenReturn(card);
  }

  @Test
  void testCreateFromCustomer() {
    Card cardResult = cardService.createFromCustomer(title, text, columnId, customer);

    verify(cardService).getColumnFromParams(columnId);
    verify(cardService).getNewCard();

    verify(card).setTitle(title);
    verify(card).setText(text);

    verify(column).addCard(card);
    verify(customer).addCard(card);

    verify(cardRepository).save(card);

    assertNotNull(cardResult);
  }
}
