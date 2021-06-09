package com.example.jprotokanban.services.comment;

import com.example.jprotokanban.models.card.Card;
import com.example.jprotokanban.models.comment.Comment;
import com.example.jprotokanban.models.comment.CommentRepository;
import com.example.jprotokanban.models.comment.CommentType;
import com.example.jprotokanban.models.customer.Customer;
import com.example.jprotokanban.models.mail.Mail;
import com.example.jprotokanban.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  Comment create(Card card, Customer customer, String text) {
    Comment comment = new Comment();
    comment.setCommentType(CommentType.EXTERNAL_INCOMING);
    comment.setCard(card);
    comment.setCustomer(customer);
    comment.setText(text);

    return commentRepository.save(comment);
  }

  Comment create(Card card, User user, String text) {
    Comment comment = new Comment();
    comment.setCommentType(CommentType.INTERNAL);
    comment.setCard(card);
    comment.setUser(user);
    comment.setText(text);

    return commentRepository.save(comment);
  }

  public Comment createCommentByCardFromMail(Card card, Customer customer, Mail mail) {
    return create(card, customer, mail.getPlainContent());
  }

}