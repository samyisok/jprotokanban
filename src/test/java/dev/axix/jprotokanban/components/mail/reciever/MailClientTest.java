package dev.axix.jprotokanban.components.mail.reciever;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class MailClientTest {

  @SpyBean
  MailClient mailClient;

  @Mock
  MimeMessage mimeMessage;

  @Test
  void testGetNewMimeMessageParser() {
    MimeMessageParser mimeMessageParser =
        mailClient.getNewMimeMessageParser(mimeMessage);

    assertNotNull(mimeMessageParser);
  }
}
