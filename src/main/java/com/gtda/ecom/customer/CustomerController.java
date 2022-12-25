package com.gtda.ecom.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gtda.ecom.data.CustomerRequest;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {
  @Autowired
  CustomerRepository customerRepository;

  @GetMapping("/users")
  public ResponseEntity<List<Customer>> getAllUsers() {
    try {
      List<Customer> users = new ArrayList<Customer>();

      customerRepository.findAll().forEach(users::add);
      if (users.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<Customer> getUser(@PathVariable("userId") long userId) {
    Optional<Customer> user = customerRepository.findById(userId);

    if (!user.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user.get(), HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<Customer> createUser(@RequestBody CustomerRequest body) {
    try {
      Customer newUser = customerRepository.save(new Customer(body, body.getPassword()));
      return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/users/{userId}")
  public ResponseEntity<Customer> updateUser(@PathVariable("userId") long userId, @RequestBody CustomerRequest body) {
    try {
      Optional<Customer> user = customerRepository.findById(userId);

      if (!user.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      Customer updatedUser = user.get();
      updatedUser.setEmail(body.getEmail());
      updatedUser.setFullName(body.getFullName());
      updatedUser.setPassword(body.getPassword());

      return new ResponseEntity<>(customerRepository.save(updatedUser), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") long userId) {
    try {
      customerRepository.deleteById(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
