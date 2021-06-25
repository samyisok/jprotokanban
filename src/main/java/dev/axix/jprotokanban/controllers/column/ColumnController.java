package dev.axix.jprotokanban.controllers.column;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import dev.axix.jprotokanban.models.column.Column;
import dev.axix.jprotokanban.services.column.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController()
@RequestMapping("api/column")
@PreAuthorize("denyAll")
public class ColumnController {
  @Autowired
  private ColumnService columnService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('USER')")
  public Column create(@Valid @RequestBody ColumnCreateInput input) {
    return columnService.create(input.getTitle(), input.getBoardId());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public Column get(@PathVariable @Positive Long id) {
    return columnService.getColumn(id);
  }

  // api/column/list?page=2&size=1
  @GetMapping("/list")
  @PreAuthorize("hasRole('USER')")
  public Page<Column> list(Pageable pageable) {
    return columnService.getAllPageable(pageable);
  }
}
