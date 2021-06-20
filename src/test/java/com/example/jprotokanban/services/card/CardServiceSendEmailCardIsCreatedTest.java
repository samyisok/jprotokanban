package com.example.jprotokanban.services.card;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.customer.Customer;
import com.example.jprotokanban.properties.MailSenderProperties;
import com.example.jprotokanban.services.mail.MailSenderService;
import com.example.jprotokanban.services.mail.TemplateList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.thymeleaf.context.Context;


@SpringBootTest
public class CardServiceSendEmailCardIsCreatedTest {

  @SpyBean
  CardService cardService;

  @MockBean
  MailSenderService mailSenderService;

  @MockBean
  MailSenderProperties mailSenderProperties;

  @Mock
  Customer customer;

  @Mock
  Card newCard;

  @Mock
  Context context;

  String fromEmail = "defaultReplyEmail";
  String toEmail = "email";
  Long ticketId = 42L;
  String subject = "title";

  @BeforeEach
  void setUp() {
    when(mailSenderProperties.getDefaultReplyEmail()).thenReturn(fromEmail);
    when(customer.getEmail()).thenReturn(toEmail);
    when(newCard.getId()).thenReturn(ticketId);
    when(newCard.getTitle()).thenReturn(subject);

    doReturn(context).when(cardService).getNewContext();
  }

  @Test
  void testSendEmailCardIsCreated() {
    cardService.sendEmailCardIsCreated(customer, newCard);

    verify(mailSenderProperties).getDefaultReplyEmail();
    verify(customer).getEmail();
    verify(newCard).getId();
    verify(newCard).getTitle();

    verify(cardService).getNewContext();

    verify(context).setVariable("ticketId", ticketId.toString());
    verify(context).setVariable("subject", subject);

    verify(mailSenderService).addMailToSendQueue(TemplateList.INCOMING_MAIL_REPLY,
        context, fromEmail, toEmail);
  }
}
