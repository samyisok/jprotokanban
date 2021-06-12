package com.example.jprotokanban.services.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

@SpringBootTest
public class TemplateManagerServiceTest {

  @Autowired
  private TemplateManagerService templateManagerService;

  @Test
  void testGetSubjectText() {
    Context context = new Context();
    context.setVariable("ticketId", 33333);
    context.setVariable("subject", "test");
    String str = templateManagerService
        .getProcessedTemplate("[#[(${ticketId})]] - Reply: [(${subject})]", context);
    assertEquals("[#33333] - Reply: test", str);
  }
}
