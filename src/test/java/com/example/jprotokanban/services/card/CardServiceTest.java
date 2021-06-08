package com.example.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class CardServiceTest {

  @SpyBean
  private CardService cardService;

  String mailSubject = "RE: [#123456] test subject 42";
  Long ticketExpected = 123456L;

  @Test
  void testShouldFindCardFromMail() {
    Optional<Long> ticketNumber = cardService.parseMailTitleForCardNumber(mailSubject);
    assertTrue(ticketNumber.isPresent());
    assertEquals(ticketExpected, ticketNumber.get());
  }

  @Test
  void testShouldNotFindCardFromMail() {
    Optional<Long> ticketNumber =
        cardService.parseMailTitleForCardNumber("#123456 test");
    assertTrue(ticketNumber.isEmpty());
  }

}
