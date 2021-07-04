package dev.axix.jprotokanban.services.user;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.axix.jprotokanban.models.role.Role;
import dev.axix.jprotokanban.models.user.MyUserDetails;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  static final String DEFAULT_ROLE = "ROLE_USER";

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  Logger getLog() {
    return log;
  }

  User getNewUser() {
    return new User();
  }

  Role getNewRole() {
    return new Role();
  }

  void checkUserAlreadyExist(String login) throws UserAlreadyExistException {
    if (!userRepository.findByUserName(login).isEmpty()) {
      throw new UserAlreadyExistException("User Already Exists");
    }
  }

  public boolean registration(String login, String password)
      throws UserAlreadyExistException {

    checkUserAlreadyExist(login);

    User user = getNewUser();
    Role role = getNewRole();
    role.setRole(DEFAULT_ROLE);
    user.addRole(role);
    user.setActive(true);
    user.setUserName(login);
    user.setPassword(passwordEncoder.encode(password));

    User createdUser = userRepository.save(user);

    boolean userCreated = (createdUser.getId() != null);

    if (userCreated) {
      getLog().info("Successfuly created user: " + createdUser.getId());
    } else {
      getLog().warn("Failed attempt to create user: " + login);
    }

    return userCreated;
  }

  public User getUserInfo(Authentication authentication) {
    MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
    Optional<User> user = userRepository.findById(myUserDetails.getId());

    return user.get();
  }
}
