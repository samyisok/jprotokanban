package com.axix.jprotokanban.services.user;

public class UserAlreadyExistException extends Exception {
  private static final long serialVersionUID = -2642313603547045635L;

  public UserAlreadyExistException(String message) {
    super(message);
  }
}
