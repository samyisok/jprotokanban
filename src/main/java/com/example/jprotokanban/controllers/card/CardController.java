package com.example.jprotokanban.controllers.card;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import com.example.jprotokanban.controllers.SuccessResponse;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.services.card.CardService;
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
@RequestMapping("api/card")
@PreAuthorize("denyAll")
public class CardController {
  @Autowired
  private CardService cardService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('USER')")
  public Card create(@Valid @RequestBody CardCreateInput input,
      Authentication authentication) {
    return cardService.createWithAuth(
        input.getTitle(),
        input.getText(),
        input.getColumnId(),
        authentication);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public Card get(@PathVariable @Positive Long id) {
    return cardService.getCard(id);
  }

  // api/card/list?page=2&size=1
  @GetMapping("/list")
  @PreAuthorize("hasRole('USER')")
  public Page<Card> list(Pageable pageable) {
    return cardService.getAllPageable(pageable);
  }

  @PostMapping("/move")
  @PreAuthorize("hasRole('USER')")
  public SuccessResponse move(@Valid @RequestBody CardMoveInput input) {
    Boolean result = cardService.moveToColumn(input.getCardId(), input.getColumnId());
    return new SuccessResponse(result);
  }
}
