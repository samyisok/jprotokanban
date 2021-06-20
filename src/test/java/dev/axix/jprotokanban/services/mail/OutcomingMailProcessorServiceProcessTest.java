package dev.axix.jprotokanban.services.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import dev.axix.jprotokanban.models.outcomingmail.OutcomingMail;
import dev.axix.jprotokanban.models.outcomingmail.OutcomingMailRepository;
import dev.axix.jprotokanban.properties.MailSenderProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class OutcomingMailProcessorServiceProcessTest {

  @MockBean
  private OutcomingMailRepository outcomingMailRepository;

  @MockBean
  private MailSenderService mailSenderService;

  @MockBean
  private MailSenderProperties mailSenderProperties;

  @Mock
  private OutcomingMail mailFirst;

  @Mock
  private OutcomingMail mailSecond;

  List<OutcomingMail> listOfMails = new ArrayList<>();

  @SpyBean
  private OutcomingMailProcessorService outcomingMailProcessorService;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    listOfMails.add(mailFirst);
    listOfMails.add(mailSecond);
    when(mailSenderProperties.getActive()).thenReturn(true);
    when(outcomingMailRepository.findBySendedFalse()).thenReturn(listOfMails);
    doReturn(log).when(outcomingMailProcessorService).getLog();
  }

  @Test
  void testProcessSuccess() throws MessagingException {
    outcomingMailProcessorService.process();
    verify(mailSenderService).sendMail(mailFirst);
    verify(mailSenderService).sendMail(mailSecond);
    verify(mailFirst).getId();
    verify(mailSecond).getId();
    verify(mailFirst).setSended(true);
    verify(mailSecond).setSended(true);
    verify(outcomingMailRepository).save(mailFirst);
    verify(outcomingMailRepository).save(mailSecond);

    verify(log).info("Prepare total mails: 2");
    verify(log, times(2)).info("Try to send mail, id: 0");
    verify(log, never()).error(any());
  }

  @Test
  void testProcessNotActive() throws MessagingException {
    when(mailSenderProperties.getActive()).thenReturn(false);

    outcomingMailProcessorService.process();
    verify(mailSenderService, never()).sendMail(mailFirst);
    verify(mailSenderService, never()).sendMail(mailSecond);
    verify(mailFirst, never()).getId();
    verify(mailSecond, never()).getId();
    verify(mailFirst, never()).setSended(true);
    verify(mailSecond, never()).setSended(true);
    verify(outcomingMailRepository, never()).save(mailFirst);
    verify(outcomingMailRepository, never()).save(mailSecond);

    verify(log).info("Mail sending is turnoff");
  }

  @Test
  void testProcessSendMailThrowsExceptionCatched() throws MessagingException {
    doThrow(new MessagingException("test")).when(mailSenderService).sendMail(mailFirst);
    outcomingMailProcessorService.process();
    verify(log).error("failed send mail, id: 0 by reason: test");
  }
}
