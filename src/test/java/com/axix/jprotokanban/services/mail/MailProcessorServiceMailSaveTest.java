package com.axix.jprotokanban.services.mail;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import com.axix.jprotokanban.components.mail.reciever.MailContainer;
import com.axix.jprotokanban.models.mail.Mail;
import com.axix.jprotokanban.models.mail.MailRepository;
import com.axix.jprotokanban.services.mail.MailProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class MailProcessorServiceMailSaveTest {


  @MockBean
  private MailRepository mailRepository;

  @SpyBean
  private MailProcessorService mailProcessorService;

  @Mock
  private MailContainer mailContainer;

  @Mock
  private Logger log;

  @Mock
  private Mail mail;

  @BeforeEach
  void setUp() throws AddressException {
    doReturn(log).when(mailProcessorService).getLog();
    doReturn(mail).when(mailProcessorService).getNewMail();

    when(mailContainer.getFrom()).thenReturn("test@test.ru");
    when(mailContainer.getTo())
        .thenReturn(List.of(new InternetAddress("test@test1.ru"),
            new InternetAddress("test2@test1.ru")));
    when(mailContainer.getCc()).thenReturn(List.of(new InternetAddress("test@test2.ru"),
        new InternetAddress("test2@test2.ru")));

    when(mailContainer.getSubject()).thenReturn("subject");
    when(mailContainer.getHasAttachments()).thenReturn(true);
    when(mailContainer.getHasHtmlContent()).thenReturn(true);
    when(mailContainer.getHasPlainContent()).thenReturn(true);
    when(mailContainer.getHtmlContent()).thenReturn("html");
    when(mailContainer.getPlainContent()).thenReturn("plain");
  }

  @Test
  void testMailSave() {
    mailProcessorService.mailSave(mailContainer);

    verify(log).info("process mailContainer of mailContainer");
    verify(mailProcessorService).getLog();
    verify(mailProcessorService).getNewMail();

    verify(mail).setFromAddr("test@test.ru");
    verify(mail).setTo("test@test1.ru, test2@test1.ru");
    verify(mail).setCc("test@test2.ru, test2@test2.ru");
    verify(mail).setSubject("subject");
    verify(mail).setHasAttachments(true);
    verify(mail).setHasHtmlContent(true);
    verify(mail).setHasPlainContent(true);
    verify(mail).setHtmlContent("html");
    verify(mail).setPlainContent("plain");

    verify(mailRepository).save(mail);
  }
}
