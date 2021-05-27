package com.example.reverseStringService.controllers;

import java.util.Map;
import javax.xml.bind.ValidationException;
import com.example.reverseStringService.services.user.UserAlreadyExistException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler({ValidationException.class, UserAlreadyExistException.class,
      AccessDeniedException.class})
  public Map<String, String> errorHandler(Exception e) {
    return Map.of("error", e.getMessage());
  }
}
