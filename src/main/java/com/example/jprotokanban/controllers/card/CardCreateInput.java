package com.example.jprotokanban.controllers.card;

public class CardCreateInput {
  private String title;
  private String text;
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
