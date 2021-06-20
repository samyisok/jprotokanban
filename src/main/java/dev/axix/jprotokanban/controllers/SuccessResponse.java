package dev.axix.jprotokanban.controllers;

public class SuccessResponse {
  private Boolean success;

  public SuccessResponse(Boolean result) {
    this.success = result;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }
}
