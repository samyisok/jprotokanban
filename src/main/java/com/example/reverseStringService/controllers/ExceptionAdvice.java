package com.example.reverseStringService.controllers;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import com.example.reverseStringService.exceptions.custom.CodeExceptionManager;
import com.example.reverseStringService.exceptions.custom.CustomError;
import com.example.reverseStringService.exceptions.custom.GenericException;
import com.example.reverseStringService.services.user.UserAlreadyExistException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler({ValidationException.class, UserAlreadyExistException.class,
      AccessDeniedException.class, EntityNotFoundException.class,
      GenericException.class})
  public Map<String, List<CustomError>> errorHandler(Exception e) {
    if (e instanceof GenericException) {
      GenericException gException = (GenericException) e;
      return Map.of("errors", List.of(gException.getCustomError()));
    }
    return Map.of("errors",
        List.of(new CustomError(e.getMessage(),
            CodeExceptionManager.getDefaultCodeError())));
  }
}
