package com.axix.jprotokanban.exceptions.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomError {
  private String message;
  private Long code;

  private List<Map<String, String>> fieldErrors = new ArrayList<>();

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

  public void appendFieldError(Map<String, String> fieldError) {
    this.fieldErrors.add(fieldError);
  }

  public List<Map<String, String>> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(List<Map<String, String>> errors) {
    this.fieldErrors = errors;
  }
}
