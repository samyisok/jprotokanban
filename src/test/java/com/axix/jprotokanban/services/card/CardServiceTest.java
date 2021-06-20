package com.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.card.Card;
import com.axix.jprotokanban.models.card.CardRepository;
import com.axix.jprotokanban.models.mail.Mail;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class CardServiceTest {

  @SpyBean
  private CardService cardService;

  @MockBean
  private CardRepository cardRepository;

  @Mock
  Card card;

  @Mock
  Mail mail;

  String mailSubject = "RE: [#123456] test subject 42";
  Long ticketExpected = 123456L;

  @Test
  void testShouldParseMailTitleForCardNumber() {
    Optional<Long> ticketNumber = cardService.parseMailTitleForCardNumber(mailSubject);
    assertTrue(ticketNumber.isPresent());
    assertEquals(ticketExpected, ticketNumber.get());
  }

  @Test
  void testShouldNotParseMailTitleForCardNumber() {
    Optional<Long> ticketNumber =
        cardService.parseMailTitleForCardNumber("#123456 test");
    assertTrue(ticketNumber.isEmpty());
  }

  @Test
  void testShouldFindCardFromMail() {
    when(cardRepository.findById(ticketExpected)).thenReturn(Optional.of(card));
    when(mail.getSubject()).thenReturn(mailSubject);
    when(cardService.parseMailTitleForCardNumber(mailSubject))
        .thenReturn(Optional.of(ticketExpected));

    Optional<Card> cardOpt = cardService.findCardFromMail(mail);
    assertTrue(cardOpt.isPresent());
    assertSame(cardOpt.get(), card);
  }

  @Test
  void testShouldNotFindCardFromMailIfMailSubjectIsEmpty() {
    when(mail.getSubject()).thenReturn("mailSubject");
    when(cardService.parseMailTitleForCardNumber(mailSubject))
        .thenReturn(Optional.empty());

    Optional<Card> cardOpt = cardService.findCardFromMail(mail);
    assertTrue(cardOpt.isEmpty());
  }

  @Test
  void testGetCard() {
    when(cardRepository.findById(123L)).thenReturn(Optional.of(card));
    Card newCard = cardService.getCard(123L);
    assertSame(card, newCard);
  }


  @Test
  void testGetCardIsEmptyThrow() {
    when(cardRepository.findById(123L)).thenReturn(Optional.empty());
    assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
        () -> cardService.getCard(123L));
  }

  @Test
  void testGetNewCard() {
    Card cardResult = cardService.getNewCard();
    assertNotNull(cardResult);
  }

  @Test
  void testGetLog() {
    Logger log = cardService.getLog();
    assertNotNull(log);
  }

}
