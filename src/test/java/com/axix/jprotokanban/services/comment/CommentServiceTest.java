package com.axix.jprotokanban.services.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.axix.jprotokanban.models.card.Card;
import com.axix.jprotokanban.models.card.CardRepository;
import com.axix.jprotokanban.models.comment.Comment;
import com.axix.jprotokanban.models.comment.CommentRepository;
import com.axix.jprotokanban.models.comment.CommentType;
import com.axix.jprotokanban.models.customer.Customer;
import com.axix.jprotokanban.models.mail.Mail;
import com.axix.jprotokanban.models.user.MyUserDetails;
import com.axix.jprotokanban.models.user.User;
import com.axix.jprotokanban.models.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


@SpringBootTest
public class CommentServiceTest {

  @SpyBean
  private CommentService commentService;

  @MockBean
  private CommentRepository commentRepository;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private CardRepository cardRepository;

  @Mock
  private Customer customer;

  @Mock
  private User user;

  @Mock
  private Card card;

  @Spy
  private Comment comment;

  @Spy
  private Mail mail;

  @Mock
  private Authentication authentication;

  @Mock
  private MyUserDetails myUserDetails;

  @Mock
  private Page<Comment> page;

  @Mock
  private Pageable pageable;

  private Long cardId = 42L;
  private Long userId = 32L;
  private Long commentId = 64L;
  private String text = "test";

  @Test
  void testCreate() {
    String text = "test";

    when(commentService.getNewComment()).thenReturn(comment);
    when(commentRepository.save(comment)).thenReturn(comment);

    commentService.create(card, customer, text);

    verify(comment, times(1)).setCommentType(CommentType.EXTERNAL_INCOMING);
    verify(comment, times(1)).setCard(card);
    verify(comment, times(1)).setCustomer(customer);
    verify(comment, times(1)).setText(text);
    verify(commentRepository, times(1)).save(comment);
  }

  @Test
  void testCreate2() {
    String text = "test";

    when(commentService.getNewComment()).thenReturn(comment);
    when(commentRepository.save(comment)).thenReturn(comment);

    commentService.create(card, user, text);

    verify(comment, times(1)).setCommentType(CommentType.INTERNAL);
    verify(comment, times(1)).setCard(card);
    verify(comment, times(1)).setUser(user);
    verify(comment, times(1)).setText(text);
    verify(commentRepository, times(1)).save(comment);
  }

  @Test
  void testCreateCommentByCardFromMail() {
    String plainContent = "test";

    doReturn(comment).when(commentService).create(card, customer, plainContent);

    mail.setPlainContent(plainContent);
    Comment commentResult =
        commentService.createCommentByCardFromMail(card, customer, mail);

    verify(commentService, times(1)).create(card, customer, plainContent);
    verify(mail, times(1)).getPlainContent();
    assertSame(comment, commentResult);
  }

  @Test
  void testCreateWithAuth() {
    doReturn(comment).when(commentService).create(card, user, text);
    when(authentication.getPrincipal()).thenReturn(myUserDetails);
    when(myUserDetails.getId()).thenReturn(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

    Comment commentResult = commentService.createWithAuth(text, cardId, authentication);

    assertSame(comment, commentResult);

    verify(authentication).getPrincipal();
    verify(myUserDetails).getId();
    verify(userRepository).findById(userId);
    verify(cardRepository).findById(cardId);
    verify(commentService).create(card, user, text);
  }


  @Test
  void testCreateWithAuthThrowExceptionNotFoundCard() {
    doReturn(comment).when(commentService).create(card, user, text);
    when(authentication.getPrincipal()).thenReturn(myUserDetails);
    when(myUserDetails.getId()).thenReturn(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

    Throwable exception =
        assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
            () -> commentService.createWithAuth(text, cardId, authentication));

    assertEquals("Entity not found (card 42 not found!)", exception.getMessage());
  }


  @Test
  void testCreateWithAuthThrowExceptionNotFoundUser() {
    doReturn(comment).when(commentService).create(card, user, text);
    when(authentication.getPrincipal()).thenReturn(myUserDetails);
    when(myUserDetails.getId()).thenReturn(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    Throwable exception =
        assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
            () -> commentService.createWithAuth(text, cardId, authentication));

    assertEquals("Entity not found (user 32 not found!)", exception.getMessage());
  }


  @Test
  void testGetAllPageable() {
    when(commentRepository.findAll(pageable)).thenReturn(page);
    Page<Comment> pageResult = commentService.getAllPageable(pageable);
    assertSame(page, pageResult);
    verify(commentRepository).findAll(pageable);
  }

  @Test
  void testGetComment() {
    when(commentRepository.findById(cardId)).thenReturn(Optional.of(comment));
    Comment commentResult = commentService.getComment(cardId);
    assertSame(comment, commentResult);
  }

  @Test
  void testGetCommentExceptionNotFoundCard() {
    when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

    Throwable exception =
        assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
            () -> commentService.getComment(commentId));

    assertEquals("Entity not found (comment 64 not found!)", exception.getMessage());
  }
}
