package com.axix.jprotokanban.components.mail.reciever;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class MailClientGetPropertiesTest {

  @SpyBean
  private MailClient mailClient;

  @Mock
  private Properties properties;

  @Test
  void testGetNewProperties() {
    Properties prop = mailClient.getNewProperties();
    assertNotNull(prop);
  }

  @Test
  void testGetProperties() {
    doReturn(properties).when(mailClient).getNewProperties();
    Properties prop = mailClient.getProperties("host.com", 123);
    verify(properties).put("mail.pop3.host", "host.com");
    verify(properties).put("mail.pop3.ssl.enable", true);
    verify(properties).put("mail.pop3.ssl.trust", "*");
    verify(properties).put("mail.pop3.port", 123);
    assertNotNull(prop);
  }
}
