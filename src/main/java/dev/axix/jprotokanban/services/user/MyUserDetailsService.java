package dev.axix.jprotokanban.services.user;

import java.util.Optional;
import dev.axix.jprotokanban.models.user.MyUserDetails;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByUserName(username);

    user.orElseThrow(() -> new UsernameNotFoundException("username not found"));

    return user.map(MyUserDetails::new).get();
  }
}
