package com.axix.jprotokanban.services.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.axix.jprotokanban.models.card.Card;
import com.axix.jprotokanban.models.customer.Customer;
import com.axix.jprotokanban.models.mail.Mail;
import com.axix.jprotokanban.models.mail.MailRepository;
import com.axix.jprotokanban.services.card.CardService;
import com.axix.jprotokanban.services.customer.CustomerParserException;
import com.axix.jprotokanban.services.customer.CustomerService;
import com.axix.jprotokanban.services.mail.CardGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class CardGeneratorServiceProcessTest {
  @MockBean
  private CustomerService customerService;

  @MockBean
  private CardService cardService;

  @MockBean
  private MailRepository mailRepository;

  @SpyBean
  private CardGeneratorService cardGeneratorService;

  @Mock
  private Mail mail1;

  @Mock
  private Mail mail2;

  List<Mail> mails = new ArrayList<>();

  @Mock
  private Logger log;

  @Mock
  private Customer customer;

  @Mock
  private Card card;

  @BeforeEach
  void setUp() throws CustomerParserException {
    mails.add(mail1);
    mails.add(mail2);

    when(mail1.getFromAddr()).thenReturn("addr1");
    when(mail2.getFromAddr()).thenReturn("addr2");

    when(mailRepository.findByProcessedFalse()).thenReturn(mails);
    when(customerService.createFromEmailString(anyString())).thenReturn(customer);
    when(cardService.createCardOrCommentFromEmail(any(Customer.class), any(Mail.class)))
        .thenReturn(card);

    doReturn(log).when(cardGeneratorService).getLog();
  }

  @Test
  void testProcess() {
    cardGeneratorService.process();

    verify(log).info("Process unprocessed mails, total: 2");
    verify(log).info("Start process: mail1");
    verify(log).info("Start process: mail2");

    verify(mail1).setProcessed(true);
    verify(mail2).setProcessed(true);

    verify(mailRepository).saveAll(mails);
  }

  @Test
  void testProcessThrowException() throws CustomerParserException {
    when(customerService.createFromEmailString(anyString()))
        .thenThrow(new CustomerParserException("Exception Message"));

    cardGeneratorService.process();

    verify(mail1).setProcessed(true);
    verify(mail2).setProcessed(true);

    verify(log).info("Process unprocessed mails, total: 2");
    verify(log).info("Start process: mail1");
    verify(log).info("Can not process mail1 -> Exception Message");
    verify(log).info("Start process: mail2");
    verify(log).info("Can not process mail2 -> Exception Message");

    verify(mailRepository).saveAll(mails);
  }
}
