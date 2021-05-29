package com.example.jprotokanban.controllers.card;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CardCreateInput {
  @NotNull
  @Size(min = 3, max = 250)
  private String title;
  @NotNull
  @Size(min = 3)
  private String text;
  @NotNull
  @Positive
  private Long columnId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getColumnId() {
    return columnId;
  }

  public void setColumnId(Long columnId) {
    this.columnId = columnId;
  }
}
