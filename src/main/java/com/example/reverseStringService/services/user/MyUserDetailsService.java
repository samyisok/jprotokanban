package com.example.reverseStringService.services.user;

import java.util.Optional;
import com.example.reverseStringService.models.user.MyUserDetails;
import com.example.reverseStringService.models.user.User;
import com.example.reverseStringService.models.user.UserRepository;
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
