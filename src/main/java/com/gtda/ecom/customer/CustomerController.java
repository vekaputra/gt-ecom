package com.gtda.ecom.customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {
  @Autowired
  CustomerRepository customerRepository;

  @GetMapping("/customers")
  public ResponseEntity<List<Customer>> getAllcustomers() {
    try {
      List<Customer> customers = new ArrayList<Customer>();

      customerRepository.findAll().forEach(customers::add);
      if (customers.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(customers, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/customers/{customerId}")
  public ResponseEntity<Customer> getcustomer(@PathVariable("customerId") long customerId) {
    Optional<Customer> customer = customerRepository.findById(customerId);

    if (!customer.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(customer.get(), HttpStatus.OK);
  }

  @GetMapping("/customers/active")
  public ResponseEntity<List<Customer>> getActiveCustomer(@RequestParam String lastLoginAt) {
    try {
      List<Customer> customers = new ArrayList<Customer>();
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
      customerRepository.findAllActiveCustomersSince(formatter.parse(lastLoginAt)).forEach(customers::add);
      if (customers.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(customers, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
