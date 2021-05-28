package com.example.reverseStringService.models.role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.example.reverseStringService.models.user.User;

@Entity
public class Role {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private User user;

  private String role;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return this.role;
  }
}
