package com.example.jprotokanban.controllers.column;

public class ColumnCreateInput {
  private String title;
  private Long boardId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }
}
