package dev.axix.jprotokanban.models.mail;

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
public class Mail {
  @Id
  @GeneratedValue
  private Long id;

  private String fromAddr;
  private String to;
  private String cc;

  @Column(length = 1000)
  private String subject;

  private Boolean hasHtmlContent;
  private Boolean hasPlainContent;
  private Boolean hasAttachments;

  @Lob
  @Column
  private String htmlContent;

  @Lob
  @Column
  private String plainContent;

  private Boolean processed = false;

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

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Boolean getHasHtmlContent() {
    return hasHtmlContent;
  }

  public void setHasHtmlContent(Boolean hasHtmlContent) {
    this.hasHtmlContent = hasHtmlContent;
  }

  public Boolean getHasPlainContent() {
    return hasPlainContent;
  }

  public void setHasPlainContent(Boolean hasPlainContent) {
    this.hasPlainContent = hasPlainContent;
  }

  public Boolean getHasAttachments() {
    return hasAttachments;
  }

  public void setHasAttachments(Boolean hasAttachments) {
    this.hasAttachments = hasAttachments;
  }


  public String getHtmlContent() {
    return htmlContent;
  }

  public void setHtmlContent(String htmlContent) {
    this.htmlContent = htmlContent;
  }


  public String getPlainContent() {
    return plainContent;
  }

  public void setPlainContent(String plainContent) {
    this.plainContent = plainContent;
  }

  public Boolean getProcessed() {
    return processed;
  }

  public void setProcessed(Boolean processed) {
    this.processed = processed;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getCc() {
    return cc;
  }

  public void setCc(String cc) {
    this.cc = cc;
  }

  public String getFromAddr() {
    return fromAddr;
  }

  public void setFromAddr(String fromAddr) {
    this.fromAddr = fromAddr;
  }

  @Override
  public String toString() {
    return "Mail [ id=" + id + ", fromAddr=" + fromAddr + ",  subject=" + subject
        + " ]";
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public Instant getModifiedDate() {
    return modifiedDate;
  }
}
