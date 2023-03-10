package com.gtda.ecom.auth;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gtda.ecom.customer.Customer;
import com.gtda.ecom.customer.CustomerRepository;
import com.gtda.ecom.data.SignUpRequest;
import com.gtda.ecom.data.UpdatePasswordRequest;
import com.gtda.ecom.data.ErrorResponse;
import com.gtda.ecom.data.ForgotPasswordRequest;
import com.gtda.ecom.data.ForgotPasswordResponse;
import com.gtda.ecom.data.JwtResponse;
import com.gtda.ecom.data.LoginRequest;
import com.gtda.ecom.data.MessageResponse;

import jakarta.validation.Valid;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest body) throws NoSuchAlgorithmException {
    Optional<Customer> existingCustomer = customerRepository.findFirstByEmail(body.getEmail());
    if (existingCustomer.isPresent()) {
      return ResponseEntity.badRequest().body(new ErrorResponse("customer already exists"));
    }

    if (!body.getPassword().equals(body.getConfirmPassword())) {
      return ResponseEntity.badRequest().body(new ErrorResponse("password do not match"));
    }

    customerRepository.save(new Customer(body, encoder.encode(body.getPassword())));
    return ResponseEntity.ok(new MessageResponse("signUp success"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest body) throws NoSuchAlgorithmException {
    Authentication auth = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
    
    Optional<Customer> existingCustomer = customerRepository.findFirstByEmail(body.getEmail());
    Customer customer = existingCustomer.get();
    customer.setLastLoginAt(new Date());
    customerRepository.save(customer);

    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = jwtUtils.generateJwtToken(auth);

    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return ResponseEntity
        .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
  }

  @PostMapping("/password/forgot")
  public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest body)
      throws NoSuchAlgorithmException {
    Optional<Customer> existingCustomer = customerRepository.findFirstByEmail(body.getEmail());
    if (!existingCustomer.isPresent()) {
      return ResponseEntity.badRequest().body(new ErrorResponse("customer do not exist"));
    }

    Customer customer = existingCustomer.get();
    String forgotPasswordToken = TokenUtils.generateRandomStringToken();
    customer.setForgotPasswordToken(forgotPasswordToken);

    customerRepository.save(customer);
    return ResponseEntity.ok().body(new ForgotPasswordResponse(
        "http://localhost:8080/api/auth/password/update/" + forgotPasswordToken, forgotPasswordToken));
  }

  @PutMapping("/password/update/{forgotPasswordToken}")
  public ResponseEntity<?> updatePassword(@PathVariable("forgotPasswordToken") String forgorPasswordToken, @Valid @RequestBody UpdatePasswordRequest body)
      throws NoSuchAlgorithmException {
    Optional<Customer> existingCustomer = customerRepository.findFirstByEmailAndForgotPasswordToken(body.getEmail(), forgorPasswordToken);
    if (!existingCustomer.isPresent()) {
      return ResponseEntity.badRequest().body(new ErrorResponse("invalid data given"));
    }

    if (!body.getPassword().equals(body.getConfirmPassword())) {
      return ResponseEntity.badRequest().body(new ErrorResponse("password do not match"));
    }

    Customer customer = existingCustomer.get();
    customer.setForgotPasswordToken(null);
    customer.setPassword(encoder.encode(body.getPassword()));
    customerRepository.save(customer);

    return ResponseEntity.ok().body(new MessageResponse("update password success"));
  }
}
