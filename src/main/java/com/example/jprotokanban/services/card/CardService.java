package com.example.jprotokanban.services.card;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.card.CardRepository;
import com.example.jprotokanban.models.column.Column;
import com.example.jprotokanban.models.column.ColumnRepository;
import com.example.jprotokanban.models.customer.Customer;
import com.example.jprotokanban.models.mail.Mail;
import com.example.jprotokanban.models.user.MyUserDetails;
import com.example.jprotokanban.models.user.User;
import com.example.jprotokanban.models.user.UserRepository;
import com.example.jprotokanban.services.filter.IncomingMailFilterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
  private IncomingMailFilterable mailFilter;

  public Card createWithAuth(String title, String text, Long columnId,
      Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    Optional<User> userOpt = userRepository.findById(userDetails.getId());

    if (userOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("user " + userDetails.getId() + " not found!");
    }

    User user = userOpt.get();

    Optional<Column> columnOpt = columnRepository.findById(columnId);
    if (columnOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("column " + columnId + " not found!");
    }
    Column column = columnOpt.get();

    Card card = new Card();
    card.setTitle(title);
    card.setText(text);

    column.addCard(card);
    user.addCard(card);

    return cardRepository.save(card);
  }

  Card createFromCustomer(String title, String text, Long columnId,
      Customer customer) {
    Optional<Column> columnOpt = columnRepository.findById(columnId);
    if (columnOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("column " + columnId + " not found!");
    }
    Column column = columnOpt.get();

    Card card = new Card();
    card.setTitle(title);
    card.setText(text);

    column.addCard(card);
    customer.addCard(card);

    return cardRepository.save(card);
  }

  public Card createCardOrCommentFromMail(Customer customer, Mail mail) {
    Optional<Card> cardOpt = findCardFromMail(mail);
    try {
      if (cardOpt.isEmpty()) {
        return createFromCustomer(mail.getSubject(), mail.getPlainContent(),
            mailFilter.getColumnId(),
            customer);
      }
    } catch (Exception e) {
      log.warn("can't create card by reason: " + e.getMessage());
    }

    // TODO create comment

    return cardOpt.get();
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
    Optional<Column> columnOpt = columnRepository.findById(columnId);
    if (columnOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("column " + columnId + " not found!");
    }

    Optional<Card> cardOpt = cardRepository.findById(cardId);
    if (cardOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("card " + cardId + " not found!");
    }
    Card card = cardOpt.get();
    Column columnOld = card.getColumn();
    Column columnNew = columnOpt.get();

    if (columnNew.getId().equals(columnOld.getId())) {
      return false;
    }

    columnOld.removeCard(card);
    columnNew.addCard(card);

    cardRepository.save(card);
    return true;
  }

}

