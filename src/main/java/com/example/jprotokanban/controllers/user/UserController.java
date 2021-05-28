package com.example.jprotokanban.controllers.user;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import com.example.jprotokanban.services.user.UserAlreadyExistException;
import com.example.jprotokanban.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("denyAll")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  AuthenticationManager authManager;


  @GetMapping("/")
  @PreAuthorize("permitAll")
  public Map<String, String> check() {
    return Map.of("check", "ok");
  }

  @PostMapping("/user/auth")
  @PreAuthorize("permitAll")
  public Map<String, String> auth(@RequestBody Map<String, String> input,
      HttpServletRequest httpRequest) {
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
  @PreAuthorize("permitAll")
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
