package com.example.jprotokanban.controllers.card;

public class CardMoveInput {
  private Long columnId;
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
