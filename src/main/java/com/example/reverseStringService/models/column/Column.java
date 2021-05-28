package com.example.reverseStringService.models.column;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.example.reverseStringService.models.board.Board;
import com.example.reverseStringService.models.card.Card;

@Entity
public class Column {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private Long wip;
  private Long position;

  @ManyToOne
  private Board board;

  @OneToMany(mappedBy = "column", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      orphanRemoval = false)
  private List<Card> cards = new ArrayList<>();

  public void addCard(Card card) {
    cards.add(card);
    card.setColumn(this);
  }

  public void removeCard(Card card) {
    cards.remove(card);
    card.setColumn(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getWip() {
    return wip;
  }

  public void setWip(Long wip) {
    this.wip = wip;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Column other = (Column) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }
}
