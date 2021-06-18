package com.example.jprotokanban.services.mail;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import com.example.jprotokanban.models.outcomingmail.OutcomingMail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@SpringBootTest
public class MailSenderServiceSendMailTest {

  @Mock
  private OutcomingMail mail;

  @Mock
  private MimeMessage message;

  @Mock
  private MimeMessageHelper helper;

  @MockBean
  private JavaMailSender emailSender;

  @SpyBean
  private MailSenderService mailSenderService;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() throws MessagingException {
    when(emailSender.createMimeMessage()).thenReturn(message);
    doReturn(helper).when(mailSenderService).getMimeMessageHelper(message, true);
    doReturn(log).when(mailSenderService).getLog();

    when(mail.getFromEmail()).thenReturn("from");
    when(mail.getToEmail()).thenReturn("to");
    when(mail.getSubject()).thenReturn("subject");
    when(mail.getPlainContent()).thenReturn("plain");
  }

  @Test
  void testSendMail() throws MessagingException {
    mailSenderService.sendMail(mail);

    verify(emailSender).createMimeMessage();
    verify(emailSender).send(message);
    verify(mailSenderService).getMimeMessageHelper(message, true);

    verify(helper).setFrom(mail.getFromEmail());
    verify(helper).setTo(mail.getToEmail());
    verify(helper).setSubject(mail.getSubject());
    verify(helper).setText(mail.getPlainContent(), mail.getHtmlContent());

    verify(log).info("mail sended, id: " + mail.getId());
  }
}
