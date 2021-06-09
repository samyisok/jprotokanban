package com.example.jprotokanban.models.board;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

  public Optional<Board> findFirstBy();

  public Optional<Board> findFirstByColumnsIsNotNull();
}
