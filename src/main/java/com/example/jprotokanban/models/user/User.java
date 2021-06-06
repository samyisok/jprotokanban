package com.example.jprotokanban.models.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User {
  @Id
  @GeneratedValue
  private Long id;

  // username is email
  @Column(unique = true)
  private String userName;
  @JsonIgnore
  private String password;
  private boolean active;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL,
      orphanRemoval = true)
  // @JoinColumn(name = "user_id")
  private Set<Role> roles = new HashSet<>();

  public void addRole(Role role) {
    roles.add(role);
    role.setUser(this);
  }

  public void removeRole(Role role) {
    roles.remove(role);
    role.setUser(null);
  }

  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL,
      orphanRemoval = false)
  private List<Card> cards = new ArrayList<>();

  public void addCard(Card card) {
    cards.add(card);
    card.setUser(this);
  }

  public void removeCard(Card card) {
    cards.remove(card);
    card.setUser(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "User [active=" + active + ", id=" + id + ", password=" + password
        + ", roles=" + roles + ", userName=" + userName + "]";
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }


}
