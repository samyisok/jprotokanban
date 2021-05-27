package com.example.reverseStringService.controllers.user;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import com.example.reverseStringService.services.user.UserAlreadyExistException;
import com.example.reverseStringService.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  AuthenticationManager authManager;


  @GetMapping("/")
  public Map<String, String> check() {
    return Map.of("check", "ok");
  }

  @ExceptionHandler({ValidationException.class, UserAlreadyExistException.class,
      AccessDeniedException.class})
  public Map<String, String> errorHandler(Exception e) {
    return Map.of("error", e.getMessage());
  }

  @PostMapping("/user/auth")
  public Map<String, String> auth(@RequestBody Map<String, String> input,
      HttpServletRequest httpRequest, HttpServletResponse httpResponse) {


    UsernamePasswordAuthenticationToken authReq =
        new UsernamePasswordAuthenticationToken(input.get("login"),
            input.get("password"));
    Authentication auth = authManager.authenticate(authReq);

    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(auth);
    HttpSession session = httpRequest.getSession(true);
    session.setAttribute("SPRING_SECURITY_CONTEXT_KEY", sc);

    return Map.of("status", "success");
  }


  @GetMapping("/user/info")
  @PreAuthorize("hasRole('USER')")
  public Map<String, String> info(Authentication authentication) {

    return Map.of("null", userService.getInfo(authentication));
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
