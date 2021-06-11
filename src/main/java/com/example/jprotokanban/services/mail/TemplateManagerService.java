package com.example.jprotokanban.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


@Service
public class TemplateManagerService {

  @Autowired
  private SpringTemplateEngine emailTemplateEngine;

  String getProcessedTemplate(String template, Context context) {
    return emailTemplateEngine.process(template, context);
  }

  String getProcessedTemplate(String template) {
    return emailTemplateEngine.process(template, new Context());
  }

  public String getPlainText(TemplateList template, Context context) {
    return getProcessedTemplate(template.getTextTemplate(), context);
  }

  public String getHtmlText(TemplateList template, Context context) {
    return getProcessedTemplate(template.getHtmlTemplate(), context);
  }

  public String getSubjectText(TemplateList template, Context context) {
    return getProcessedTemplate(template.getSubjectTemplate(), context);
  }

  public String getPlainText(TemplateList template) {
    return getProcessedTemplate(template.getTextTemplate());
  }

  public String getHtmlText(TemplateList template) {
    return getProcessedTemplate(template.getHtmlTemplate());
  }

  public String getSubjectText(TemplateList template) {
    return getProcessedTemplate(template.getSubjectTemplate());
  }


}

