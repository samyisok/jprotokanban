package com.axix.jprotokanban.services.mail;

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

  public String getPlainText(TemplateList template, Context context) {
    return getProcessedTemplate(template.getTextTemplate(), context);
  }

  public String getHtmlText(TemplateList template, Context context) {
    return getProcessedTemplate(template.getHtmlTemplate(), context);
  }

  public String getSubjectText(TemplateList template, Context context) {
    return getProcessedTemplate(template.getSubjectTemplate(), context);
  }
}

