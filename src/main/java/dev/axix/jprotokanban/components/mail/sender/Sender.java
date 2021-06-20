package dev.axix.jprotokanban.components.mail.sender;

import java.util.Properties;
import dev.axix.jprotokanban.properties.MailSenderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class Sender {

  @Autowired
  private MailSenderProperties mailSenderProperties;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(mailSenderProperties.getHost());
    mailSender.setPort(mailSenderProperties.getPort());

    mailSender.setUsername(mailSenderProperties.getUser());
    mailSender.setPassword(mailSenderProperties.getPassword());

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.smtps.ssl.checkserveridentity", "true");
    props.put("mail.smtps.ssl.trust", "*");
    props.put("mail.debug", mailSenderProperties.getDebug());

    return mailSender;
  }
}
