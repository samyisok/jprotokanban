package com.axix.jprotokanban.exceptions.custom;

import java.lang.reflect.InvocationTargetException;

public enum CodeExceptionManager {
  // Error Code Convention 31000-31999 custom codes
  NOT_FOUND("Entity not found", 31001L, GenericException.class),
  VALIDATION_ERROR("Validation error", 31002L, GenericException.class),
  INVALID_PARAMS("Invalid Params", 31003L, GenericException.class),
  CAN_NOT_CREATE_ENTITY("Can not create entity", 31005L, GenericException.class),
  EMPTY_OUTCOMING_MAIL("Empty outcoming mail", 31004L, GenericException.class);

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

  public GenericException getThrowableException(String extraMessage) {
    String msgFormat = extraMessage.isEmpty() ? "" : " (" + extraMessage + ")";
    try {
      return this.getExceptionClass().getConstructor(String.class, Long.class)
          .newInstance(this.message + msgFormat, this.code);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    return new GenericException("Unexpected Error", DEFAUL_CODE);
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
    return getThrowableException("");
  }



  public static Long getDefaultCodeError() {
    return DEFAUL_CODE;
  }
}
