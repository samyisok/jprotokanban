package com.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.card.Card;
import com.axix.jprotokanban.models.customer.Customer;
import com.axix.jprotokanban.models.mail.Mail;
import com.axix.jprotokanban.services.comment.CommentService;
import com.axix.jprotokanban.services.filter.IncomingMailFilterable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class CardServiceCreateCardOrCommentFromEmailTest {

  @SpyBean
  CardService cardService;

  @MockBean
  CommentService commentService;

  @MockBean
  IncomingMailFilterable mailFilter;

  Long columnId = 42L;

  @Mock
  Mail mail;

  String mailSubject = "mailSubject";
  String mailPlainContent = "mailPlainContent";

  @Mock
  Customer customer;

  @Mock
  Card card;

  @Mock
  Card newCard;

  @Mock
  Logger log;

  @BeforeEach
  void setUp() throws Exception {
    doReturn(Optional.of(card)).when(cardService).findCardFromMail(mail);

    when(mail.getSubject()).thenReturn(mailSubject);
    when(mail.getPlainContent()).thenReturn(mailPlainContent);
    when(mailFilter.getColumnId()).thenReturn(columnId);

    doReturn(newCard).when(cardService).createFromCustomer(mailSubject,
        mailPlainContent, columnId, customer);
    doNothing().when(cardService).sendEmailCardIsCreated(customer, newCard);
    doReturn(log).when(cardService).getLog();
  }

  @Test
  void testCreateCardOrCommentFromEmailCardAlreadyExists() {
    Card cardResult = cardService.createCardOrCommentFromEmail(customer, mail);

    verify(cardService).findCardFromMail(mail);
    verify(commentService).createCommentByCardFromMail(card, customer, mail);

    assertNotNull(cardResult);
    assertSame(card, cardResult);
  }

  @Test
  void testCreateCardOrCommentFromEmailCardNotExists() {
    doReturn(Optional.empty()).when(cardService).findCardFromMail(mail);

    Card cardResult = cardService.createCardOrCommentFromEmail(customer, mail);

    verify(cardService).findCardFromMail(mail);
    verify(cardService).createFromCustomer(mailSubject, mailPlainContent, columnId,
        customer);
    verify(cardService).sendEmailCardIsCreated(customer, newCard);

    assertNotNull(cardResult);
    assertSame(newCard, cardResult);
  }

  @Test
  void testCreateCardOrCommentFromEmailCardNotExistsThrowsException() {
    doReturn(Optional.empty()).when(cardService).findCardFromMail(mail);

    doThrow(new RuntimeException("Generic Error")).when(cardService)
        .createFromCustomer(mailSubject, mailPlainContent, columnId, customer);

    Exception e =
        assertThrows(CodeExceptionManager.CAN_NOT_CREATE_ENTITY.getExceptionClass(),
            () -> cardService.createCardOrCommentFromEmail(customer, mail));

    assertEquals("Can not create entity (can't create card by a reason: Generic Error)",
        e.getMessage());

    verify(log).warn("can't create card by a reason: Generic Error");
  }
}
