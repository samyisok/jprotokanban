package com.example.jprotokanban.services.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.example.jprotokanban.models.outcomingmail.OutcomingMail;
import com.example.jprotokanban.models.outcomingmail.OutcomingMailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailSenderServiceAddMailToSendQueueTest {

  @MockBean
  private TemplateManagerService templateManagerService;

  @MockBean
  private OutcomingMailRepository outcomingMailRepository;

  @SpyBean
  private MailSenderService mailSenderService;

  @Mock
  private OutcomingMail mail;

  @Mock
  private Logger log;

  private String htmlText = "html";
  private String plainText = "plain";
  private String subjectText = "subject";
  private String fromEmail = "from@email.com";
  private String toEmail = "to@email.com";

  @Mock
  private TemplateList template;

  @Mock
  private Context context;

  @BeforeEach
  void setUp() {
    doReturn(mail).when(mailSenderService).getNewOutcomingMail();
    doReturn(log).when(mailSenderService).getLog();
    when(templateManagerService.getHtmlText(template, context)).thenReturn(htmlText);
    when(templateManagerService.getPlainText(template, context)).thenReturn(plainText);
    when(templateManagerService.getSubjectText(template, context))
        .thenReturn(subjectText);
  }

  @Test
  void testAddMailToSendQueue() {
    mailSenderService.addMailToSendQueue(template, context, fromEmail, toEmail);

    verify(outcomingMailRepository).save(mail);

    verify(mail).setToEmail(toEmail);
    verify(mail).setFromEmail(fromEmail);
    verify(mail).setHtmlContent(htmlText);
    verify(mail).setPlainContent(plainText);
    verify(mail).setSubject(subjectText);

    verify(templateManagerService).getHtmlText(template, context);
    verify(templateManagerService).getPlainText(template, context);
    verify(templateManagerService).getSubjectText(template, context);

    verify(log).info("mail queued, id: 0");
  }

  @Test
  void testAddMailToSendQueueThrowHtml() {
    when(templateManagerService.getHtmlText(template, context)).thenReturn("");

    Throwable exception =
        assertThrows(CodeExceptionManager.EMPTY_OUTCOMING_MAIL.getExceptionClass(),
            () -> mailSenderService.addMailToSendQueue(template, context, fromEmail,
                toEmail));

    verify(log).error("Empty Mail for Send", mail);

    assertEquals("Empty outcoming mail", exception.getMessage());
  }


  @Test
  void testAddMailToSendQueueThrowPlain() {
    when(templateManagerService.getPlainText(template, context)).thenReturn("");

    Throwable exception =
        assertThrows(CodeExceptionManager.EMPTY_OUTCOMING_MAIL.getExceptionClass(),
            () -> mailSenderService.addMailToSendQueue(template, context, fromEmail,
                toEmail));

    verify(log).error("Empty Mail for Send", mail);

    assertEquals("Empty outcoming mail", exception.getMessage());
  }

  @Test
  void testAddMailToSendQueueThrowSubject() {
    when(templateManagerService.getSubjectText(template, context)).thenReturn("");

    Throwable exception =
        assertThrows(CodeExceptionManager.EMPTY_OUTCOMING_MAIL.getExceptionClass(),
            () -> mailSenderService.addMailToSendQueue(template, context, fromEmail,
                toEmail));

    verify(log).error("Empty Mail for Send", mail);

    assertEquals("Empty outcoming mail", exception.getMessage());
  }
}
