package com.example.reverseStringService.controllers.user;

import java.util.Map;
import javax.xml.bind.ValidationException;
import com.example.reverseStringService.services.user.UserAlreadyExistException;
import com.example.reverseStringService.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/")
  public Map<String, String> check() {
    return Map.of("check", "ok");
  }

  @ExceptionHandler({ValidationException.class, UserAlreadyExistException.class})
  public Map<String, String> errorHandler(Exception e) {
    return Map.of("error", e.getMessage());
  }

  @PostMapping("/user/registration")
  public Map<String, Boolean> registration(
      @RequestBody UserInputRegistration userRegistrationData)
      throws ValidationException, UserAlreadyExistException {


    if (userRegistrationData.isValid()) {
      boolean result = userService.registration(userRegistrationData);
      return Map.of("isSuccess", result);
    } else {
      throw new ValidationException("Wrong input");
    }
  }


}
