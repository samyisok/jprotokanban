package dev.axix.jprotokanban.services.mail;

import java.util.List;
import javax.mail.MessagingException;
import dev.axix.jprotokanban.models.outcomingmail.OutcomingMail;
import dev.axix.jprotokanban.models.outcomingmail.OutcomingMailRepository;
import dev.axix.jprotokanban.properties.MailSenderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutcomingMailProcessorService {

  private static final Logger log =
      LoggerFactory.getLogger(OutcomingMailProcessorService.class);


  @Autowired
  private OutcomingMailRepository outcomingMailRepository;

  @Autowired
  private MailSenderService mailSenderService;

  @Autowired
  private MailSenderProperties mailSenderProperties;

  // for tests
  Logger getLog() {
    return log;
  }


  @Transactional
  public void process() {
    if (!mailSenderProperties.getActive()) {
      getLog().info("Mail sending is turnoff");
      return;
    }

    List<OutcomingMail> mails = outcomingMailRepository.findBySendedFalse();

    getLog().info("Prepare total mails: " + mails.size());
    for (OutcomingMail outcomingMail : mails) {
      getLog().info("Try to send mail, id: " + outcomingMail.getId());
      try {
        mailSenderService.sendMail(outcomingMail);
      } catch (MessagingException | RuntimeException e) {
        getLog().error("failed send mail, id: " + outcomingMail.getId() + " by reason: "
            + e.getMessage());
      } finally {
        // mark mail anyway
        outcomingMail.setSended(true);
        outcomingMailRepository.save(outcomingMail);
      }
    }
  }
}
