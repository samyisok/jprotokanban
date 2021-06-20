package dev.axix.jprotokanban.services.user;

import dev.axix.jprotokanban.controllers.user.UserInputRegistration;
import dev.axix.jprotokanban.models.role.Role;
import dev.axix.jprotokanban.models.user.MyUserDetails;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;
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
    role.setRole("ROLE_USER");
    user.addRole(role);
    user.setActive(true);
    user.setUserName(userDataRegistration.getLogin());
    user.setPassword(passwordEncoder.encode(userDataRegistration.getPassword1()));

    User createdUser = userRepository.save(user);

    return (createdUser.getId() != null);
  }

  public User getInfo(Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    User user = userRepository.getById(userDetails.getId());

    return user;
  }
}
