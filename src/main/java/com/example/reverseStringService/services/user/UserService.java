package com.example.reverseStringService.services.user;

import com.example.reverseStringService.controllers.user.UserInputRegistration;
import com.example.reverseStringService.models.role.Role;
import com.example.reverseStringService.models.user.MyUserDetails;
import com.example.reverseStringService.models.user.User;
import com.example.reverseStringService.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    Role role = new Role();
    role.setRole("DEFAULT");
    user.addRole(role);;
    user.setActive(true);
    user.setUserName(userDataRegistration.getLogin());
    user.setPassword(passwordEncoder.encode(userDataRegistration.getPassword1()));



    User createdUser = userRepository.save(user);

    return (createdUser.getId() != null);
  }

  public String getInfo(Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    User user = userRepository.getById(userDetails.getId());

    return user.toString();
  }
}
