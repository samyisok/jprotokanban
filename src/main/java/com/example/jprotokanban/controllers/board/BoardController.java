package com.example.jprotokanban.controllers.board;

import com.example.jprotokanban.models.board.Board;
import com.example.jprotokanban.services.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/board")
@PreAuthorize("denyAll")
public class BoardController {
  @Autowired
  private BoardService boardService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('USER')")
  public Board create(@RequestBody BoardCreateInput input) {
    return boardService.create(input);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public Board getBoard(@PathVariable Long id) {
    return boardService.getBoard(id);
  }

  // api/board/list?page=2&size=1
  @GetMapping("/list")
  @PreAuthorize("hasRole('USER')")
  public Page<Board> listPageable(Pageable pageable) {
    return boardService.getAllPageable(pageable);
  }
}
