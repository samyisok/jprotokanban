package com.example.jprotokanban.models.role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.example.jprotokanban.models.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Role {
  @Id
  @GeneratedValue
  private Long id;

  private String role;

  @JsonIgnore
  @ManyToOne
  private User user;


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

  // @Override
  // public String toString() {
  // return this.role;
  // }
}
