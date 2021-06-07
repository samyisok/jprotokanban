package com.example.jprotokanban.models.customer;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import com.example.jprotokanban.models.card.Card;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Customer {

  @Id
  @GeneratedValue
  private Long id;
  private String name;

  @Email
  @Column(unique = true)
  private String email;


  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
          CascadeType.DETACH},
      orphanRemoval = false)
  private List<Card> cards = new ArrayList<>();

  public void addCard(Card card) {
    cards.add(card);
    card.setCustomer(this);
  }

  public void removeCard(Card card) {
    cards.remove(card);
    card.setCustomer(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
