package dev.axix.jprotokanban.services.customer;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dev.axix.jprotokanban.models.customer.Customer;
import dev.axix.jprotokanban.models.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerService {
  private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

  @Autowired
  private CustomerRepository customerRepository;

  Logger getLog() {
    return log;
  }

  Customer getNewCustomer() {
    return new Customer();
  }

  public Customer create(String name, String email) {
    Optional<Customer> existedCustomer = customerRepository.findByEmail(email);
    if (existedCustomer.isPresent()) {
      // update name if exist
      if (!name.isEmpty()) {
        getLog().info("Update customer name: " + name + " " + email);
        Customer currentCustomer = existedCustomer.get();
        currentCustomer.setName(name);
        return customerRepository.save(currentCustomer);
      }
      getLog().info("Customer alredy exist, do nothing: " + email);
      return existedCustomer.get();
    }

    Customer customer = getNewCustomer();
    customer.setName(name);
    customer.setEmail(email);

    getLog().info("Create Customer: " + name + " " + email);
    return customerRepository.save(customer);
  }


  public Customer createFromEmailString(String from) throws CustomerParserException {
    Map<String, String> customerData = parseFrom(from);

    return create(customerData.get("name"), customerData.get("email"));
  }


  Map<String, String> parseFrom(String from) throws CustomerParserException {
    String regexp = "(.+)<(.+)>";
    Pattern pattern = Pattern.compile(regexp);
    Matcher matcher = pattern.matcher(from);
    if (matcher.matches()) {
      return Map.of("name", matcher.group(1).strip(), "email",
          matcher.group(2).strip());
    }

    if (Pattern.compile(".+@.+\\..+").matcher(from).matches()) {
      return Map.of("name", "", "email", from.strip());
    }

    throw new CustomerParserException("Invalid from field");
  }
}
