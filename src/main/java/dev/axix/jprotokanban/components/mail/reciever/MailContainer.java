package dev.axix.jprotokanban.components.mail.reciever;

import java.util.List;
import javax.mail.Address;

public class MailContainer {
  private String from;
  private List<Address> to;
  private List<Address> cc;
  private String subject;
  private Boolean hasHtmlContent;
  private Boolean hasPlainContent;
  private Boolean hasAttachments;
  private String htmlContent;
  private String plainContent;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
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

  @Override
  public String toString() {
    return "MailContainter [cc=" + cc + ", from=" + from + ", hasAttachments="
        + hasAttachments + ", hasHtmlContent=" + hasHtmlContent + ", hasPlainContent="
        + hasPlainContent + ", htmlContent=" + htmlContent + ", plainContent="
        + plainContent + ", subject=" + subject + ", to=" + to + "]";
  }

  public List<Address> getTo() {
    return to;
  }

  public void setTo(List<Address> to) {
    this.to = to;
  }

  public List<Address> getCc() {
    return cc;
  }

  public void setCc(List<Address> cc) {
    this.cc = cc;
  }

}
