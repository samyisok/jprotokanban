package dev.axix.jprotokanban.controllers.comment;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import dev.axix.jprotokanban.models.comment.Comment;
import dev.axix.jprotokanban.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController()
@RequestMapping("api/comment")
@PreAuthorize("denyAll")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('USER')")
  public Comment create(@Valid @RequestBody CommentCreateInput input,
      Authentication authentication) {


    return commentService.createWithAuth(input.getText(), input.getCardId(),
        authentication);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public Comment get(@PathVariable @Positive Long id) {
    return commentService.getComment(id);
  }

  // api/comment/list?page=2&size=1
  @GetMapping("/list")
  @PreAuthorize("hasRole('USER')")
  public Page<Comment> list(Pageable pageable) {
    return commentService.getAllPageable(pageable);
  }
}
