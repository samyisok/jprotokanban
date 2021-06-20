package com.axix.jprotokanban.services.mail;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.outcomingmail.OutcomingMail;
import com.axix.jprotokanban.models.outcomingmail.OutcomingMailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;



@Service
public class MailSenderService {

  private static final Logger log = LoggerFactory.getLogger(MailSenderService.class);

  @Autowired
  private TemplateManagerService templateManagerService;

  @Autowired
  private OutcomingMailRepository outcomingMailRepository;

  @Autowired
  private JavaMailSender emailSender;

  Logger getLog() {
    return log;
  }

  OutcomingMail getNewOutcomingMail() {
    return new OutcomingMail();
  }

  public void addMailToSendQueue(TemplateList template, Context context,
      String fromEmail, String toEmail) {

    OutcomingMail mail = getNewOutcomingMail();
    mail.setToEmail(toEmail);
    mail.setFromEmail(fromEmail);

    String html = templateManagerService.getHtmlText(template, context);
    String plain = templateManagerService.getPlainText(template, context);
    String subject = templateManagerService.getSubjectText(template, context);

    mail.setHtmlContent(html);
    mail.setPlainContent(plain);
    mail.setSubject(subject);

    if (html.isBlank() || plain.isBlank() || subject.isBlank()) {
      getLog().error("Empty Mail for Send", mail);
      throw CodeExceptionManager.EMPTY_OUTCOMING_MAIL.getThrowableException();
    }

    outcomingMailRepository.save(mail);

    getLog().info("mail queued, id: " + mail.getId());
  }

  MimeMessageHelper getMimeMessageHelper(MimeMessage message, Boolean isMultipart)
      throws MessagingException {
    return new MimeMessageHelper(message, isMultipart);
  }

  public void sendMail(OutcomingMail mail) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();

    MimeMessageHelper helper = getMimeMessageHelper(message, true);

    helper.setFrom(mail.getFromEmail());
    helper.setTo(mail.getToEmail());
    helper.setSubject(mail.getSubject());
    helper.setText(mail.getPlainContent(), mail.getHtmlContent());

    emailSender.send(message);

    getLog().info("mail sended, id: " + mail.getId());
  }
}
