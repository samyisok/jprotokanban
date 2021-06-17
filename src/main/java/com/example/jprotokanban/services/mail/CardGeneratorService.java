package com.example.jprotokanban.services.mail;

import java.util.List;
import com.example.jprotokanban.models.customer.Customer;
import com.example.jprotokanban.models.mail.Mail;
import com.example.jprotokanban.models.mail.MailRepository;
import com.example.jprotokanban.services.card.CardService;
import com.example.jprotokanban.services.customer.CustomerParserException;
import com.example.jprotokanban.services.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CardGeneratorService {
  private static final Logger log = LoggerFactory.getLogger(CardGeneratorService.class);

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CardService cardService;

  @Autowired
  private MailRepository mailRepository;

  Logger getLog() {
    return log;
  }

  @Transactional
  public void process() {
    List<Mail> mails = mailRepository.findByProcessedFalse();

    getLog().info("Process unprocessed mails, total: " + mails.size());
    for (Mail mail : mails) {
      getLog().info("Start process: " + mail.toString());
      try {
        Customer customer = customerService.createFromEmailString(mail.getFromAddr());
        cardService.createCardOrCommentFromEmail(customer, mail);
      } catch (CustomerParserException e) {
        getLog().info("Can not process " + mail + " -> " + e.getMessage());
      } finally {
        // mark as processed in any way
        mail.setProcessed(true);
      }
    }

    mailRepository.saveAll(mails);
  }
}
