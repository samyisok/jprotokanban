package dev.axix.jprotokanban.services.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.customer.Customer;


@SpringBootTest
public class CustomerServiceTest {

  @SpyBean
  CustomerService customerService;

  @Test
  void testGetLog() {
    Logger log = customerService.getLog();
    assertNotNull(log);
  }

  @Test
  void testGetNewCustomer() {
    Customer customer = customerService.getNewCustomer();
    assertNotNull(customer);
  }
}
