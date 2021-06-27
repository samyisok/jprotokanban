package dev.axix.jprotokanban.services.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.customer.Customer;

@SpringBootTest
public class CustomerServiceCreateFromEmailStringTest {

  @SpyBean
  CustomerService customerService;

  String name = "vasya";
  String email = "email@email.com";

  @Spy
  Map<String, String> customerData =
      Map.of("name", name, "email", email);

  String from = name + " <" + email + ">";

  @Mock
  Customer customer;

  @BeforeEach
  void setUp() throws CustomerParserException {
    doReturn(customerData).when(customerService).parseFrom(from);
    doReturn(customer).when(customerService).create(name, email);
  }

  @Test
  void testCreateFromEmailString() throws CustomerParserException {
    Customer customerResult = customerService.createFromEmailString(from);
    assertNotNull(customerResult);
    assertSame(customer, customerResult);
    verify(customerService).parseFrom(from);
    verify(customerService).create(name, email);
  }
}
