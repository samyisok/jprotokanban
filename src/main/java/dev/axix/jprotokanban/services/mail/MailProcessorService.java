package dev.axix.jprotokanban.services.mail;

import java.util.List;
import java.util.stream.Collectors;
import dev.axix.jprotokanban.components.mail.reciever.MailContainer;
import dev.axix.jprotokanban.components.mail.reciever.MailReceivable;
import dev.axix.jprotokanban.models.mail.Mail;
import dev.axix.jprotokanban.models.mail.MailRepository;
import dev.axix.jprotokanban.properties.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailProcessorService {

  private static final Logger log = LoggerFactory.getLogger(MailProcessorService.class);

  @Autowired
  private MailReceivable mailClient;

  @Autowired
  private MailProperties mailProperties;

  @Autowired
  private MailRepository mailRepository;

  Logger getLog() {
    return log;
  }

  Mail getNewMail() {
    return new Mail();
  }

  void mailSave(MailContainer mailContainer) {
    getLog().info("process mailContainer of " + mailContainer);

    Mail mail = getNewMail();
    mail.setFromAddr(mailContainer.getFrom());
    mail.setTo(mailContainer.getTo().stream().map(addr -> addr.toString())
        .collect(Collectors.joining(", ")));
    mail.setCc(mailContainer.getCc().stream().map(addr -> addr.toString())
        .collect(Collectors.joining(", ")));
    mail.setSubject(mailContainer.getSubject());
    mail.setHasAttachments(mailContainer.getHasAttachments());
    mail.setHasHtmlContent(mailContainer.getHasHtmlContent());
    mail.setHasPlainContent(mailContainer.getHasPlainContent());
    mail.setHtmlContent(mailContainer.getHtmlContent());
    mail.setPlainContent(mailContainer.getPlainContent());

    mailRepository.save(mail);
  }

  @Transactional
  public void process() {
    if (!mailProperties.getActive()) {
      getLog().info("Mail retrieving is turn off!");
      return;
    }

    List<MailContainer> listOfMails =
        mailClient.receivePop3Email(
            mailProperties.getHost(),
            mailProperties.getPort(),
            mailProperties.getUser(),
            mailProperties.getPassword(),
            mailProperties.getFolder());

    getLog().info("Get mails total to process: " + listOfMails.size());

    for (MailContainer mailContainer : listOfMails) {
      mailSave(mailContainer);
    }

    getLog().info("Processing is done");
  }
}
