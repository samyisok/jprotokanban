package com.example.reverseStringService.models.board;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.example.reverseStringService.models.column.Column;

@Entity
public class Board {
  @Id
  @GeneratedValue
  private Long id;
  private String title;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Column> columns = new ArrayList<>();


  public void addColumn(Column column) {
    columns.add(column);
    column.setBoard(this);
  }


  public void removeColumn(Column column) {
    columns.remove(column);
    column.setBoard(null);
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

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }



}
