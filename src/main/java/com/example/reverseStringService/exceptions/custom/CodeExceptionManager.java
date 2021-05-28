package com.example.reverseStringService.exceptions.custom;

import java.lang.reflect.InvocationTargetException;

public enum CodeExceptionManager {
  // Error Code Convention 31000-31999 custom codes
  NOT_FOUND("Entity not found", 31001L, GenericException.class);

  private static Long DEFAUL_CODE = 31000L;
  private String message;
  private Long code;
  private Class<? extends GenericException> exceptionClass;

  private CodeExceptionManager(String message, Long code,
      Class<? extends GenericException> exceptionClass) {
    this.message = message;
    this.code = code;
    this.exceptionClass = exceptionClass;
  }

  public String getMessage() {
    return message;
  }

  public Long getCode() {
    return code;
  }

  public Class<? extends GenericException> getExceptionClass() {
    return exceptionClass;
  }

  public GenericException getThrowableException() {
    try {
      return this.getExceptionClass().getConstructor(String.class, Long.class)
          .newInstance(this.message, this.code);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    return new GenericException("Unexpected Error", DEFAUL_CODE);
  }


  public static Long getDefaultCodeError() {
    return DEFAUL_CODE;
  }
}
