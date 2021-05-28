package com.example.reverseStringService.exceptions.custom;


public class CustomError {
  private String message;
  private Long code;

  public CustomError(String message, Long codeError) {
    this.message = message;
    this.code = codeError;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
    this.code = code;
  }
}
