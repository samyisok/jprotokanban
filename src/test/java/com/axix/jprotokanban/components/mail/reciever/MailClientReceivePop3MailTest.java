package com.axix.jprotokanban.components.mail.reciever;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import com.axix.jprotokanban.properties.MailProperties;
import com.sun.mail.pop3.POP3Store;
import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class MailClientReceivePop3MailTest {

  @SpyBean
  MailClient mailClient;

  @Mock
  Properties properties;

  @Mock
  MailProperties mailProperties;

  @Mock
  Session emailSession;

  @Mock
  POP3Store emailStore;

  @Mock
  Folder emailFolder;

  @Mock
  MimeMessage message1;

  @Mock
  MimeMessage message2;

  @Mock
  MimeMessageParser mimeMessageParser1;

  @Mock
  MimeMessageParser mimeMessageParser2;

  @Mock
  MailContainer mailContainer1;

  @Mock
  MailContainer mailContainer2;

  @Mock
  Logger log;

  String host = "host.com";
  Integer port = 123;
  String user = "user";
  String password = "password";
  String folder = "INBOX";


  @BeforeEach
  void setUp() throws Exception {
    doReturn(properties).when(mailClient).getProperties(host, port);
    doReturn(emailSession).when(mailClient).getSession(properties);

    when(mimeMessageParser1.parse()).thenReturn(mimeMessageParser1);
    when(mimeMessageParser2.parse()).thenReturn(mimeMessageParser2);

    doReturn(mimeMessageParser1).when(mailClient)
        .getNewMimeMessageParser(message1);
    doReturn(mimeMessageParser2).when(mailClient)
        .getNewMimeMessageParser(message2);

    doReturn(mailContainer1).when(mailClient)
        .fillMailContainer(mimeMessageParser1);
    doReturn(mailContainer2).when(mailClient)
        .fillMailContainer(mimeMessageParser2);

    doReturn(log).when(mailClient).getLog();

    when(mailProperties.getDebug()).thenReturn(false);
    when(emailSession.getStore("pop3")).thenReturn(emailStore);
    when(emailStore.getFolder(folder)).thenReturn(emailFolder);
    when(emailFolder.getMessages()).thenReturn(new Message[] {message1, message2});
  }

  @Test
  void testReceivePop3Email() throws Exception {
    List<MailContainer> listOfMails =
        mailClient.receivePop3Email(host, port, user, password, folder);

    verify(mailClient).getProperties(host, port);
    verify(mailClient).getSession(properties);
    verify(emailSession).setDebug(false);
    verify(emailSession).getStore("pop3");
    verify(emailStore).connect(user, password);
    verify(emailStore).getFolder(folder);
    verify(emailFolder).open(Folder.READ_WRITE);
    verify(emailFolder).getMessages();
    verify(log).info("Get messages total: 2");
    verify(mailClient).getNewMimeMessageParser(message1);
    verify(mailClient).getNewMimeMessageParser(message2);
    verify(mimeMessageParser1).parse();
    verify(mimeMessageParser2).parse();
    verify(mailClient).fillMailContainer(mimeMessageParser1);
    verify(mailClient).fillMailContainer(mimeMessageParser2);
    verify(message1).setFlag(Flags.Flag.DELETED, true);
    verify(message2).setFlag(Flags.Flag.DELETED, true);

    verify(emailFolder).close(true);
    verify(emailStore).close();

    assertEquals(2, listOfMails.size());
    assertArrayEquals(new MailContainer[] {mailContainer1, mailContainer2},
        listOfMails.toArray());
  }

  @Test
  void testReceivePop3EmailThrowMessagingException() throws MessagingException {
    when(emailFolder.getMessages()).thenThrow(new MessagingException("test"));

    mailClient.receivePop3Email(host, port, user, password, folder);

    verify(log)
        .warn("Common error with mail server: javax.mail.MessagingException: test");
  }


  @Test
  void testReceivePop3EmailThroweException() throws Exception {
    when(mimeMessageParser1.parse()).thenThrow(new Exception("text"));

    mailClient.receivePop3Email(host, port, user, password, folder);

    verify(log)
        .warn("Error when parsing message: java.lang.Exception: text");
  }

}
