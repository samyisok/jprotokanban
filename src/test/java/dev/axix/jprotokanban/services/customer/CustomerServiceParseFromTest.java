package dev.axix.jprotokanban.services.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class CustomerServiceParseFromTest {

  @SpyBean
  CustomerService customerService;

  String from1 = "vasya <email@email.com>";
  Map<String, String> expectedParse1 =
      Map.of("name", "vasya", "email", "email@email.com");
  String from2 = "only@email.com";
  Map<String, String> expectedParse2 = Map.of("name", "", "email", "only@email.com");

  @Test
  void testParseFrom1() throws CustomerParserException {
    Map<String, String> result = customerService.parseFrom(from1);
    assertEquals(expectedParse1.get("name"), result.get("name"));
    assertEquals(expectedParse1.get("email"), result.get("email"));
  }


  @Test
  void testParseFrom2() throws CustomerParserException {
    Map<String, String> result = customerService.parseFrom(from2);
    assertEquals(expectedParse2.get("name"), result.get("name"));
    assertEquals(expectedParse2.get("email"), result.get("email"));
  }

  @Test
  void testParseFromException1() {
    Exception e = assertThrows(CustomerParserException.class,
        () -> customerService.parseFrom("brokenline"));
    assertEquals("Invalid from field", e.getMessage());
  }

  @Test
  void testParseFromException2() {
    Exception e = assertThrows(CustomerParserException.class,
        () -> customerService.parseFrom(""));
    assertEquals("Invalid from field", e.getMessage());
  }
}
