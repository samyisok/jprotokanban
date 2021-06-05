package com.example.jprotokanban.services.mail;

import java.util.List;
import com.example.jprotokanban.models.mail.Mail;
import com.example.jprotokanban.models.mail.MailRepository;
import com.example.jprotokanban.services.customer.CustomerException;
import com.example.jprotokanban.services.customer.CustomerParserException;
import com.example.jprotokanban.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CardGeneratorService {
  private static final Logger log = LoggerFactory.getLogger(CardGeneratorService.class);

  @Autowired
  private CustomerService customerService;

  @Autowired
  private MailRepository mailRepository;

  public void process() {
    List<Mail> mails = mailRepository.findByProcessedFalse();

    log.info("Process unprocessed mails, total:" + mails.size());
    for (Mail mail : mails) {
      log.info("Start process:" + mail.toString());

      try {
        customerService.createFromString(mail.getFromAddr());
      } catch (CustomerParserException e) {
        log.info("Can not process " + mail + " -> " + e.getMessage());
        // skip mail
        mail.setProcessed(true);
      }
    }

    mailRepository.saveAll(mails);
  }
}
