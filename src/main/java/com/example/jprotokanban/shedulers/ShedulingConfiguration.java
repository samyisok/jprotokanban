package com.example.jprotokanban.shedulers;

import com.example.jprotokanban.services.mail.CardGeneratorService;
import com.example.jprotokanban.services.mail.MailProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ShedulingConfiguration {

  @Autowired
  private MailProcessorService mailProcessorService;

  @Autowired
  private CardGeneratorService cardGeneratorService;

  @Scheduled(fixedDelay = 60000, initialDelay = 10000)
  public void mailRetrievingTask() {
    mailProcessorService.process();
  }

  @Scheduled(fixedDelay = 60000, initialDelay = 15000)
  public void generateCardsTask() {
    cardGeneratorService.process();
  }

}
