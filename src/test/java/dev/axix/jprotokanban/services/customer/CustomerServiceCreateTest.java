package dev.axix.jprotokanban.services.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.customer.Customer;
import dev.axix.jprotokanban.models.customer.CustomerRepository;


@SpringBootTest
public class CustomerServiceCreateTest {

  @SpyBean
  CustomerService customerService;

  @MockBean
  CustomerRepository customerRepository;

  @Mock
  Customer customer;

  @Mock
  Customer newCustomer;

  String name = "vasya";
  String email = "email";

  @Mock
  Logger log;

  @BeforeEach
  void setUp() {
    when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());
    when(customerRepository.save(newCustomer)).thenReturn(newCustomer);
    doReturn(newCustomer).when(customerService).getNewCustomer();
    doReturn(log).when(customerService).getLog();
  }

  @Test
  void testCreate() {
    Customer customerResult = customerService.create(name, email);
    assertNotNull(customerResult);
    assertSame(newCustomer, customerResult);
    verify(customerRepository).findByEmail(email);
    verify(customerService).getNewCustomer();
    verify(newCustomer).setName(name);
    verify(newCustomer).setEmail(email);
    verify(log).info("Create Customer: " + name + " " + email);
    verify(customerRepository).save(newCustomer);
  }

  @Test
  void testCreateIfExists() {
    when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
    Customer customerResult = customerService.create("", email);
    assertNotNull(customerResult);
    assertSame(customer, customerResult);
    verify(customerRepository).findByEmail(email);
    verify(log).info("Customer alredy exist, do nothing: " + email);
  }


  @Test
  void testCreateIfExistsAndNameNotEmpty() {
    when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
    when(customerRepository.save(customer)).thenReturn(customer);
    Customer customerResult = customerService.create("ivan", email);
    assertNotNull(customerResult);
    assertSame(customer, customerResult);
    verify(log).info("Update customer name: " + "ivan" + " " + email);
    verify(customer).setName("ivan");
    verify(customerRepository).save(customer);
  }
}
