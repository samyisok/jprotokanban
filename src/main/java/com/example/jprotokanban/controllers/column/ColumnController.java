package com.example.jprotokanban.controllers.column;

import com.example.jprotokanban.models.column.Column;
import com.example.jprotokanban.services.column.ColumnService;
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
@RequestMapping("api/column")
@PreAuthorize("denyAll")
public class ColumnController {
  @Autowired
  private ColumnService columnService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('USER')")
  public Column create(@RequestBody ColumnCreateInput input) {
    return columnService.create(input);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public Column get(@PathVariable Long id) {
    return columnService.getBoard(id);
  }

  // api/column/list?page=2&size=1
  @GetMapping("/list")
  @PreAuthorize("hasRole('USER')")
  public Page<Column> list(Pageable pageable) {
    return columnService.getAllPageable(pageable);
  }
}
