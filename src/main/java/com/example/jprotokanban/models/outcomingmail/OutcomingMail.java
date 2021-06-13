package com.example.jprotokanban.models.outcomingmail;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class OutcomingMail {

  @Id
  @GeneratedValue
  private Long id;

  private String fromEmail;
  private String toEmail;

  @Column(length = 1000)
  private String subject;

  @Lob
  @Column
  private String plainContent;

  @Lob
  @Column
  private String htmlContent;

  private boolean sended;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedDate
  private Instant modifiedDate;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFromEmail() {
    return fromEmail;
  }

  public void setFromEmail(String fromEmail) {
    this.fromEmail = fromEmail;
  }

  public String getToEmail() {
    return toEmail;
  }

  public void setToEmail(String toEmail) {
    this.toEmail = toEmail;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getPlainContent() {
    return plainContent;
  }

  public void setPlainContent(String plainContent) {
    this.plainContent = plainContent;
  }

  public String getHtmlContent() {
    return htmlContent;
  }

  public void setHtmlContent(String htmlContent) {
    this.htmlContent = htmlContent;
  }

  public boolean isSended() {
    return sended;
  }

  public void setSended(boolean sended) {
    this.sended = sended;
  }

  @Override
  public String toString() {
    return "OutcomingMail [fromEmail=" + fromEmail + ", htmlContent=" + htmlContent
        + ", id=" + id + ", plainContent=" + plainContent + ", sended=" + sended
        + ", subject=" + subject + ", toEmail=" + toEmail + "]";
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public Instant getModifiedDate() {
    return modifiedDate;
  }

}
