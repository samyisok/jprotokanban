package dev.axix.jprotokanban.controllers;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.exceptions.custom.CustomError;
import dev.axix.jprotokanban.exceptions.custom.GenericException;
import dev.axix.jprotokanban.services.user.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler(value = {
      ValidationException.class,
      UserAlreadyExistException.class,
      AccessDeniedException.class,
      EntityNotFoundException.class,
      GenericException.class,
      MethodArgumentTypeMismatchException.class,
      MethodArgumentNotValidException.class,
      ConstraintViolationException.class
  })
  @ResponseStatus(HttpStatus.OK)

  public Map<String, List<CustomError>> errorHandler(Exception e) {
    if (e instanceof GenericException) {
      GenericException gException = (GenericException) e;
      return Map.of("errors", List.of(gException.getCustomError()));
    }

    CustomError customError = new CustomError(e.getMessage(),
        CodeExceptionManager.getDefaultCodeError());

    if (e instanceof ConstraintViolationException) {
      customError.setMessage(CodeExceptionManager.INVALID_PARAMS.getMessage());
      customError.setCode(CodeExceptionManager.INVALID_PARAMS.getCode());

      ConstraintViolationException ex = (ConstraintViolationException) e;

      for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
        customError.appendFieldError(
            Map.of(violation.getPropertyPath().toString().replaceFirst(".+\\.", ""),
                violation.getMessage()));
      }
    }

    if (e instanceof MethodArgumentNotValidException) {
      customError.setMessage(CodeExceptionManager.VALIDATION_ERROR.getMessage());
      customError.setCode(CodeExceptionManager.VALIDATION_ERROR.getCode());

      MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
      ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        customError.appendFieldError(Map.of(fieldName, errorMessage));
      });
    }

    return Map.of("errors", List.of(customError));
  }
}
