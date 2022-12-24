package com.gtda.ecom.controller;

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

import com.gtda.ecom.data.UserPayload;
import com.gtda.ecom.model.User;
import com.gtda.ecom.repository.UserRepository;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  UserRepository userRepository;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    try {
      List<User> users = new ArrayList<User>();

      userRepository.findAll().forEach(users::add);
      if (users.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user.get(), HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody UserPayload body) {
    try {
      User newUser = userRepository.save(new User(body));
      return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/users/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable("userId") long userId, @RequestBody UserPayload body) {
    try {
      Optional<User> user = userRepository.findById(userId);

      if (!user.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      User updatedUser = user.get();
      updatedUser.setEmail(body.getEmail());
      updatedUser.setPhoneNumber(body.getPhoneNumber());
      updatedUser.setFirstName(body.getFirstName());
      updatedUser.setLastName(body.getLastName());
      updatedUser.setProfileUrl(body.getProfileUrl());

      return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") long userId) {
    try {
      userRepository.deleteById(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
