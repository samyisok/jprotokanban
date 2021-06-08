package com.example.jprotokanban.models.comment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.customer.Customer;
import com.example.jprotokanban.models.user.User;

@Entity
public class Comment {

  @Id
  @GeneratedValue
  private Long id;

  @Lob
  @Column
  private String text;

  @ManyToOne
  private Card card;

  @OneToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
          CascadeType.DETACH})
  private User user;

  @OneToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
          CascadeType.DETACH})
  private Customer customer;

  private CommentType commentType = CommentType.INTERNAL;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public CommentType getCommentType() {
    return commentType;
  }

  public void setCommentType(CommentType commentType) {
    this.commentType = commentType;
  }
}
