package com.example.reverseStringService.controllers.board;

import com.example.reverseStringService.models.board.Board;
import com.example.reverseStringService.services.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("board")
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
}
