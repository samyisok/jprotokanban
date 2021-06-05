package com.example.jprotokanban.components.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import com.example.jprotokanban.properties.MailProperties;
import com.sun.mail.pop3.POP3Store;
import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailClient implements MailReceivable {

  private static final Logger log = LoggerFactory.getLogger(MailClient.class);

  @Autowired
  private MailProperties mailProperties;

  @Override
  public List<MailContainer> receivePop3Email(String host, Integer port, String user,
      String password,
      String folder) {

    List<MailContainer> listOfMails = new ArrayList<>();

    try {
      Properties properties = new Properties();
      properties.put("mail.pop3.host", host);
      properties.put("mail.pop3.ssl.enable", true);
      properties.put("mail.pop3.ssl.trust", "*");
      properties.put("mail.pop3.port", port);
      Session emailSession = Session.getDefaultInstance(properties);
      emailSession.setDebug(mailProperties.getDebug());

      POP3Store emailStore = (POP3Store) emailSession.getStore("pop3");
      emailStore.connect(user, password);

      Folder emailFolder = emailStore.getFolder(folder);
      emailFolder.open(Folder.READ_WRITE);

      Message[] messages = emailFolder.getMessages();
      log.info("Get messages total: " + messages.length);
      for (int i = 0; i < messages.length; i++) {
        Message message = messages[i];
        MimeMessage mimeMessage = (MimeMessage) message;
        MimeMessageParser mimeMessageParser =
            new MimeMessageParser(mimeMessage).parse();

        MailContainer mailContainter = new MailContainer();
        mailContainter.setFrom(mimeMessageParser.getFrom());
        mailContainter.setTo(mimeMessageParser.getTo());
        mailContainter.setCc(mimeMessageParser.getCc());
        mailContainter.setSubject(mimeMessageParser.getSubject());
        mailContainter.setHasAttachments(mimeMessageParser.hasAttachments());
        mailContainter.setHasHtmlContent(mimeMessageParser.hasHtmlContent());
        mailContainter.setHasPlainContent(mimeMessageParser.hasPlainContent());
        mailContainter.setHtmlContent(mimeMessageParser.getHtmlContent());
        mailContainter.setPlainContent(mimeMessageParser.getPlainContent());
        listOfMails.add(mailContainter);
        message.setFlag(Flags.Flag.DELETED, true);
      }

      emailFolder.close(true);
      emailStore.close();
    } catch (MessagingException e) {
      log.warn("Common error with mail server: " + e.toString() + e.getMessage());
    } catch (IOException e) {
      log.warn("IO error" + e.toString() + e.getMessage());
    } catch (Exception e) {
      log.warn("Error when parsing message: " + e.toString() + e.getMessage());
      e.printStackTrace();
    }
    return listOfMails;
  }


}
