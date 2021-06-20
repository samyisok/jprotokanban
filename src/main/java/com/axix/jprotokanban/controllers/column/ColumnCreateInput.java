package com.axix.jprotokanban.controllers.column;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ColumnCreateInput {
  @NotEmpty
  @Size(min = 3, max = 250)
  private String title;
  @NotNull
  @Positive
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
