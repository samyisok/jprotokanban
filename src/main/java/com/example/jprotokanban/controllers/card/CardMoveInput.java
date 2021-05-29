package com.example.jprotokanban.controllers.card;

import javax.validation.constraints.Positive;
import org.springframework.lang.NonNull;

public class CardMoveInput {
  @NonNull
  @Positive
  private Long columnId;
  @NonNull
  @Positive
  private Long cardId;

  public Long getColumnId() {
    return columnId;
  }

  public void setColumnId(Long columnId) {
    this.columnId = columnId;
  }

  public Long getCardId() {
    return cardId;
  }

  public void setCardId(Long cardId) {
    this.cardId = cardId;
  }
}
