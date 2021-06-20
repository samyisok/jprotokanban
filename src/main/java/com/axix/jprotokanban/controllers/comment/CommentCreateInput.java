package com.axix.jprotokanban.controllers.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class CommentCreateInput {
  @NotEmpty
  @Size(min = 2)
  private String text;

  @NotNull
  @Positive
  private Long cardId;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getCardId() {
    return cardId;
  }

  public void setCardId(Long cardId) {
    this.cardId = cardId;
  }
}
