package com.example.jprotokanban.components.mail.reciever;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class MailClientFillMailContainerTest {

  @SpyBean
  MailClient mailClient;

  @Mock
  MailContainer mailContainer;

  @Mock
  MimeMessageParser mimeMessageParser;

  @Test
  void testGetNewMailContainer() {
    MailContainer mailCont = mailClient.getNewMailContainer();
    assertNotNull(mailCont);
  }

  @Test
  void testFillMailContainer() throws Exception {
    doReturn(mailContainer).when(mailClient).getNewMailContainer();

    List<Address> listTo = List.of(new InternetAddress("mailTo@mail.com"));
    List<Address> listCc = List.of(new InternetAddress("mailCc@mail.com"));


    when(mimeMessageParser.getFrom()).thenReturn("from");
    when(mimeMessageParser.getTo())
        .thenReturn(listTo);
    when(mimeMessageParser.getCc())
        .thenReturn(listCc);
    when(mimeMessageParser.getSubject()).thenReturn("subject");
    when(mimeMessageParser.hasAttachments()).thenReturn(true);
    when(mimeMessageParser.hasHtmlContent()).thenReturn(true);
    when(mimeMessageParser.hasPlainContent()).thenReturn(true);
    when(mimeMessageParser.getHtmlContent()).thenReturn("html");
    when(mimeMessageParser.getPlainContent()).thenReturn("plain");

    MailContainer mailCont = mailClient.fillMailContainer(mimeMessageParser);

    verify(mailContainer).setFrom("from");
    verify(mailContainer).setTo(listTo);
    verify(mailContainer).setCc(listCc);
    verify(mailContainer).setSubject("subject");
    verify(mailContainer).setHasAttachments(true);
    verify(mailContainer).setHasHtmlContent(true);
    verify(mailContainer).setHasPlainContent(true);
    verify(mailContainer).setHtmlContent("html");
    verify(mailContainer).setPlainContent("plain");

    assertNotNull(mailCont);
  }
}
