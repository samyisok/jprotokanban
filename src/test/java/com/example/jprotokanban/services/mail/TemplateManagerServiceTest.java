package com.example.jprotokanban.services.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TemplateManagerServiceTest {

  @Autowired
  private TemplateManagerService templateManagerService;

  @Test
  void testGetProcessedTemplate() {
    templateManagerService.getProcessedTemplate("text/incoming-mail-reply.txt");
  }
}
