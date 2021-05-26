package com.example.reverseStringService.controllers.user;

import java.util.Map;
import javax.xml.bind.ValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @GetMapping("/")
  public Map<String, String> check() {
    return Map.of("check", "ok");
  }

  @ExceptionHandler({ValidationException.class})
  public Map<String, String> errorHandler(Exception e) {
    return Map.of("error", e.getMessage());
  }

  @PostMapping("/user/registration")
  public Map<String, Boolean> registration(
      @RequestBody UserInputRegistration registrationData) throws ValidationException {

    if (registrationData.isValid()) {
      return Map.of("isSuccess", true);
    } else {
      throw new ValidationException("Wrong input");
    }
  }


}
