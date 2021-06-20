package dev.axix.jprotokanban.services.mail;

public enum TemplateList {
  INCOMING_MAIL_REPLY(
      "html/incoming-mail-reply.html",
      "text/incoming-mail-reply.txt",
      "[#[(${ticketId})]] - Reply: [(${subject})]");

  private String htmlTemplate;
  private String textTemplate;
  private String subjectTemplate;

  private TemplateList(String htmlTemplate, String textTemplate,
      String subjectTemplate) {
    this.htmlTemplate = htmlTemplate;
    this.textTemplate = textTemplate;
    this.subjectTemplate = subjectTemplate;
  }

  public String getHtmlTemplate() {
    return htmlTemplate;
  }

  public String getTextTemplate() {
    return textTemplate;
  }

  public String getSubjectTemplate() {
    return subjectTemplate;
  }

}
