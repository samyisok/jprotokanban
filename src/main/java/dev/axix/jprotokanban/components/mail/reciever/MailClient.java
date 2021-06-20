package dev.axix.jprotokanban.components.mail.reciever;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import dev.axix.jprotokanban.properties.MailProperties;
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

  Logger getLog() {
    return log;
  }

  Properties getNewProperties() {
    return new Properties();
  }

  Properties getProperties(String host, Integer port) {
    Properties properties = getNewProperties();
    properties.put("mail.pop3.host", host);
    properties.put("mail.pop3.ssl.enable", true);
    properties.put("mail.pop3.ssl.trust", "*");
    properties.put("mail.pop3.port", port);
    return properties;
  }

  MailContainer getNewMailContainer() {
    return new MailContainer();
  }

  MailContainer fillMailContainer(MimeMessageParser mimeMessageParser)
      throws Exception {
    MailContainer mailContainer = getNewMailContainer();

    mailContainer.setFrom(mimeMessageParser.getFrom());
    mailContainer.setTo(mimeMessageParser.getTo());
    mailContainer.setCc(mimeMessageParser.getCc());
    mailContainer.setSubject(mimeMessageParser.getSubject());
    mailContainer.setHasAttachments(mimeMessageParser.hasAttachments());
    mailContainer.setHasHtmlContent(mimeMessageParser.hasHtmlContent());
    mailContainer.setHasPlainContent(mimeMessageParser.hasPlainContent());
    mailContainer.setHtmlContent(mimeMessageParser.getHtmlContent());
    mailContainer.setPlainContent(mimeMessageParser.getPlainContent());

    return mailContainer;
  }


  Session getSession(Properties properties) {
    return Session.getDefaultInstance(properties);
  }

  MimeMessageParser getNewMimeMessageParser(MimeMessage mimeMessage) {
    return new MimeMessageParser(mimeMessage);
  }

  @Override
  public List<MailContainer> receivePop3Email(
      String host,
      Integer port,
      String user,
      String password,
      String folder) {

    List<MailContainer> listOfMails = new ArrayList<>();

    try {
      Properties properties = getProperties(host, port);

      Session emailSession = getSession(properties);
      emailSession.setDebug(mailProperties.getDebug());

      POP3Store emailStore = (POP3Store) emailSession.getStore("pop3");
      emailStore.connect(user, password);

      Folder emailFolder = emailStore.getFolder(folder);
      emailFolder.open(Folder.READ_WRITE);

      Message[] messages = emailFolder.getMessages();
      getLog().info("Get messages total: " + messages.length);
      for (int i = 0; i < messages.length; i++) {
        Message message = messages[i];
        MimeMessage mimeMessage = (MimeMessage) message;
        MimeMessageParser mimeMessageParser =
            getNewMimeMessageParser(mimeMessage).parse();

        MailContainer mailContainer = fillMailContainer(mimeMessageParser);

        listOfMails.add(mailContainer);
        message.setFlag(Flags.Flag.DELETED, true);
      }

      emailFolder.close(true);
      emailStore.close();
    } catch (MessagingException e) {
      getLog().warn("Common error with mail server: " + e.toString());
    } catch (Exception e) {
      getLog().warn("Error when parsing message: " + e.toString());
    }
    return listOfMails;
  }


}
