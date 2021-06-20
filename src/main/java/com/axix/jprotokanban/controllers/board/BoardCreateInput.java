package com.axix.jprotokanban.controllers.board;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BoardCreateInput {
  @NotNull
  @Size(min = 3, max = 250)
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
