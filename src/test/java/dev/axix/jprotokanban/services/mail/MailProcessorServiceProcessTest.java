package dev.axix.jprotokanban.services.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import dev.axix.jprotokanban.components.mail.reciever.MailContainer;
import dev.axix.jprotokanban.components.mail.reciever.MailReceivable;
import dev.axix.jprotokanban.models.mail.MailRepository;
import dev.axix.jprotokanban.properties.MailProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class MailProcessorServiceProcessTest {

  @MockBean
  private MailProperties mailProperties;

  @MockBean
  private MailReceivable mailClient;

  @MockBean
  private MailRepository mailRepository;

  @SpyBean
  private MailProcessorService mailProcessorService;

  @Mock
  private MailContainer mailContainer1;

  @Mock
  private MailContainer mailContainer2;

  List<MailContainer> listOfMails = new ArrayList<>();

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    when(mailProperties.getActive()).thenReturn(true);

    listOfMails.add(mailContainer1);
    listOfMails.add(mailContainer2);

    when(mailClient.receivePop3Email(
        mailProperties.getHost(),
        mailProperties.getPort(),
        mailProperties.getUser(),
        mailProperties.getPassword(),
        mailProperties.getFolder())).thenReturn(listOfMails);

    doNothing().when(mailProcessorService).mailSave(any());
    doReturn(log).when(mailProcessorService).getLog();
  }

  @Test
  void testProcess() {
    mailProcessorService.process();

    verify(mailProcessorService).mailSave(mailContainer1);
    verify(mailProcessorService).mailSave(mailContainer2);

    verify(mailClient).receivePop3Email(
        mailProperties.getHost(),
        mailProperties.getPort(),
        mailProperties.getUser(),
        mailProperties.getPassword(),
        mailProperties.getFolder());

    verify(log).info("Get mails total to process: 2");
    verify(log).info("Processing is done");
  }

  @Test
  void testNotProcess() {
    when(mailProperties.getActive()).thenReturn(false);
    mailProcessorService.process();

    verify(log).info("Mail retrieving is turn off!");

    verify(mailProcessorService, never()).mailSave(mailContainer1);
    verify(mailProcessorService, never()).mailSave(mailContainer2);
  }

}
