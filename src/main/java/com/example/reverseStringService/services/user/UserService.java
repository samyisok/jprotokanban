package com.example.reverseStringService.services.user;

import com.example.reverseStringService.controllers.user.UserInputRegistration;
import com.example.reverseStringService.models.user.User;
import com.example.reverseStringService.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public boolean registration(UserInputRegistration userDataRegistration)
      throws UserAlreadyExistException {

    if (!userRepository.findByUserName(userDataRegistration.getLogin()).isEmpty()) {
      throw new UserAlreadyExistException("User Already Exists");
    }

    User user = new User();
    user.setRoles("DEFAULT");
    user.setActive(true);
    user.setUserName(userDataRegistration.getLogin());
    user.setPassword(passwordEncoder.encode(userDataRegistration.getPassword1()));

    User createdUser = userRepository.save(user);

    return (createdUser.getId() != null);
  }
}
