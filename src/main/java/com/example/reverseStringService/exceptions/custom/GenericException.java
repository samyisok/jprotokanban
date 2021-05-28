package com.example.reverseStringService.exceptions.custom;

public class GenericException extends RuntimeException {
  private static final long serialVersionUID = 338677627573053973L;
  private Long codeError;

  public GenericException(String message) {
    super(message);
  }

  public GenericException() {
  }

  public GenericException(String message, Long codeError) {
    super(message);
    this.codeError = codeError;
  }


  public Long getCodeError() {
    return codeError;
  }

  public void setCodeError(Long codeError) {
    this.codeError = codeError;
  }

  public CustomError getCustomError() {
    return new CustomError(getMessage(), getCodeError());
  }

}
