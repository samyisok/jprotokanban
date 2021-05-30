package com.example.jprotokanban.services.card;

import java.util.Optional;
import com.example.jprotokanban.controllers.card.CardCreateInput;
import com.example.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.card.CardRepository;
import com.example.jprotokanban.models.column.Column;
import com.example.jprotokanban.models.column.ColumnRepository;
import com.example.jprotokanban.models.user.MyUserDetails;
import com.example.jprotokanban.models.user.User;
import com.example.jprotokanban.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CardService {
  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private ColumnRepository columnRepository;

  @Autowired
  private UserRepository userRepository;

  public Card create(CardCreateInput input, Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    Optional<User> userOpt = userRepository.findById(userDetails.getId());

    if (userOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("user " + userDetails.getId() + " not found!");
    }

    User user = userOpt.get();

    Optional<Column> columnOpt = columnRepository.findById(input.getColumnId());
    if (columnOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("column " + input.getColumnId() + " not found!");
    }
    Column column = columnOpt.get();

    Card card = new Card();
    card.setTitle(input.getTitle());
    card.setText(input.getText());

    column.addCard(card);
    user.addCard(card);

    return cardRepository.save(card);
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

