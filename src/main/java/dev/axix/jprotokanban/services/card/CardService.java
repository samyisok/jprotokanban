package dev.axix.jprotokanban.services.card;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.models.card.Card;
import dev.axix.jprotokanban.models.card.CardRepository;
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.models.column.ColumnRepository;
import dev.axix.jprotokanban.models.customer.Customer;
import dev.axix.jprotokanban.models.mail.Mail;
import dev.axix.jprotokanban.models.user.MyUserDetails;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;
import dev.axix.jprotokanban.properties.MailSenderProperties;
import dev.axix.jprotokanban.services.comment.CommentService;
import dev.axix.jprotokanban.services.filter.IncomingMailFilterable;
import dev.axix.jprotokanban.services.mail.MailSenderService;
import dev.axix.jprotokanban.services.mail.TemplateList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
public class CardService {
  private static final Logger log = LoggerFactory.getLogger(CardService.class);

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private ColumnRepository columnRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommentService commentService;

  @Autowired
  private IncomingMailFilterable mailFilter;

  @Autowired
  private MailSenderService mailSenderService;

  @Autowired
  private MailSenderProperties mailSenderProperties;

  Logger getLog() {
    return log;
  }

  Card getNewCard() {
    return new Card();
  }

  Context getNewContext() {
    return new Context();
  }

  User getUserFromAuth(Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    Optional<User> userOpt = userRepository.findById(userDetails.getId());

    if (userOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("user " + userDetails.getId() + " not found!");
    }

    return userOpt.get();
  }

  Column getColumnFromParams(Long columnId) {
    Optional<Column> columnOpt = columnRepository.findById(columnId);

    if (columnOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("column " + columnId + " not found!");
    }

    return columnOpt.get();
  }

  public Card createWithAuth(String title, String text, Long columnId,
      Authentication authentication) {

    User user = getUserFromAuth(authentication);

    Column column = getColumnFromParams(columnId);

    Card card = getNewCard();
    card.setTitle(title);
    card.setText(text);

    column.addCard(card);
    user.addCard(card);

    return cardRepository.save(card);
  }

  Card createFromCustomer(String title, String text, Long columnId,
      Customer customer) {

    Column column = getColumnFromParams(columnId);

    Card card = getNewCard();
    card.setTitle(title);
    card.setText(text);

    column.addCard(card);
    customer.addCard(card);

    // TODO LOGS
    return cardRepository.save(card);
  }

  public Card createCardOrCommentFromEmail(Customer customer, Mail mail) {
    Optional<Card> cardOpt = findCardFromMail(mail);
    if (cardOpt.isEmpty()) {
      try {
        Card newCard =
            createFromCustomer(mail.getSubject(), mail.getPlainContent(),
                mailFilter.getColumnId(),
                customer);

        sendEmailCardIsCreated(customer, newCard);
        return newCard;

      } catch (Exception e) {
        String msg = "can't create card by a reason: " + e.getMessage();
        getLog().warn(msg);
        throw CodeExceptionManager.CAN_NOT_CREATE_ENTITY.getThrowableException(msg);
      }
    } else {
      commentService.createCommentByCardFromMail(cardOpt.get(), customer, mail);
    }

    return cardOpt.get();
  }

  void sendEmailCardIsCreated(Customer customer, Card newCard) {
    String fromEmail = mailSenderProperties.getDefaultReplyEmail();
    String toEmail = customer.getEmail();
    String ticketId = newCard.getId().toString();
    String subject = newCard.getTitle();

    Context context = getNewContext();
    context.setVariable("ticketId", ticketId);
    context.setVariable("subject", subject);

    mailSenderService.addMailToSendQueue(TemplateList.INCOMING_MAIL_REPLY, context,
        fromEmail, toEmail);
  }

  Optional<Card> findCardFromMail(Mail mail) {
    Optional<Long> ticketNumber = parseMailTitleForCardNumber(mail.getSubject());
    if (ticketNumber.isEmpty()) {
      return Optional.empty();
    }

    return cardRepository.findById(ticketNumber.get());
  }

  Optional<Long> parseMailTitleForCardNumber(String title) {
    String regex = "\\[#(\\d+)\\]";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(title);

    if (matcher.find()) {
      return Optional.of(Long.valueOf(matcher.group(1)));
    }

    return Optional.empty();
  }

  public Card getCard(Long id) {
    Optional<Card> card = cardRepository.findById(id);
    if (card.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("card " + id + " not found!");
    }
    return card.get();
  }

  public Page<Card> getAllPageable(Pageable pageable) {
    return cardRepository.findAll(pageable);
  }

  public boolean moveToColumn(Long cardId, Long columnId) {
    Card card = getCard(cardId);
    Column columnOld = card.getColumn();
    Column columnNew = getColumnFromParams(columnId);

    if (columnNew.getId().equals(columnOld.getId())) {
      return false;
    }

    columnOld.removeCard(card);
    columnNew.addCard(card);

    cardRepository.save(card);
    return true;
  }

}

