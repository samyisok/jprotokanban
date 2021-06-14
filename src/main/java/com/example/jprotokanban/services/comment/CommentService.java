package com.example.jprotokanban.services.comment;

import java.util.Optional;
import com.example.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.card.CardRepository;
import com.example.jprotokanban.models.comment.Comment;
import com.example.jprotokanban.models.comment.CommentRepository;
import com.example.jprotokanban.models.comment.CommentType;
import com.example.jprotokanban.models.customer.Customer;
import com.example.jprotokanban.models.mail.Mail;
import com.example.jprotokanban.models.user.MyUserDetails;
import com.example.jprotokanban.models.user.User;
import com.example.jprotokanban.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private UserRepository userRepository;

  // for tests
  Comment getNewComment() {
    return new Comment();
  }

  Comment create(Card card, Customer customer, String text) {
    Comment comment = getNewComment();

    comment.setCommentType(CommentType.EXTERNAL_INCOMING);
    comment.setCard(card);
    comment.setCustomer(customer);
    comment.setText(text);

    return commentRepository.save(comment);
  }

  Comment create(Card card, User user, String text) {
    Comment comment = getNewComment();

    comment.setCommentType(CommentType.INTERNAL);
    comment.setCard(card);
    comment.setUser(user);
    comment.setText(text);

    return commentRepository.save(comment);
  }

  public Comment createCommentByCardFromMail(Card card, Customer customer, Mail mail) {
    return create(card, customer, mail.getPlainContent());
  }


  public Comment createWithAuth(String text, Long cardId,
      Authentication authentication) {

    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    Optional<User> userOpt = userRepository.findById(userDetails.getId());

    if (userOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("user " + userDetails.getId() + " not found!");
    }

    User user = userOpt.get();

    Optional<Card> cardOpt = cardRepository.findById(cardId);
    if (cardOpt.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("card " + cardId + " not found!");
    }
    Card card = cardOpt.get();

    return create(card, user, text);
  }

  public Comment getComment(Long id) {
    Optional<Comment> comment = commentRepository.findById(id);
    if (comment.isEmpty()) {
      throw CodeExceptionManager.NOT_FOUND
          .getThrowableException("comment " + id + " not found!");
    }
    return comment.get();
  }

  public Page<Comment> getAllPageable(Pageable pageable) {
    return commentRepository.findAll(pageable);
  }

}
