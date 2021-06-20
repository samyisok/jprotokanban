package com.axix.jprotokanban.services.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@SpringBootTest
public class TemplateManagerServiceTest {

  @SpyBean
  private TemplateManagerService templateManagerService;

  @Mock
  private TemplateList templateList;

  @Mock
  private Context context;

  @MockBean
  private SpringTemplateEngine emailTemplateEngine;


  @BeforeEach
  void setUp() {
    when(templateList.getTextTemplate()).thenReturn("textTemplate");
    when(templateList.getHtmlTemplate()).thenReturn("htmlTemplate");
    when(templateList.getSubjectTemplate()).thenReturn("subjectTemplate");
  }

  @Test
  void testGetProcessedTemplate() {
    when(emailTemplateEngine.process("template", context)).thenReturn("result");

    String result = templateManagerService.getProcessedTemplate("template", context);

    assertEquals("result", result);
    verify(emailTemplateEngine).process("template", context);
  }

  @Test
  void testGetHtmlText() {
    doReturn("html").when(templateManagerService).getProcessedTemplate(
        "htmlTemplate",
        context);

    String result = templateManagerService.getHtmlText(templateList, context);
    assertEquals("html", result);

    verify(templateList).getHtmlTemplate();
    verify(templateManagerService).getProcessedTemplate(templateList.getHtmlTemplate(),
        context);
  }

  @Test
  void testGetPlainText() {
    doReturn("text").when(templateManagerService).getProcessedTemplate(
        "textTemplate",
        context);

    String result = templateManagerService.getPlainText(templateList, context);
    assertEquals("text", result);

    verify(templateList).getTextTemplate();
    verify(templateManagerService).getProcessedTemplate(templateList.getTextTemplate(),
        context);
  }

  @Test
  void testGetSubjectText() {
    doReturn("subject").when(templateManagerService).getProcessedTemplate(
        "subjectTemplate",
        context);

    String result = templateManagerService.getSubjectText(templateList, context);
    assertEquals("subject", result);

    verify(templateList).getSubjectTemplate();
    verify(templateManagerService).getProcessedTemplate(
        templateList.getSubjectTemplate(),
        context);
  }



}
