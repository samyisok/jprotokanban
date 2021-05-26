package com.example.reverseStringService.services.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
public class StringServiceTest {

  @SpyBean
  StringService stringService;

  @Test
  void testReverseString() {
    String str = stringService.reverseString("Hello World!");
    assertEquals(str, "!dlroW olleH");
  }
}
